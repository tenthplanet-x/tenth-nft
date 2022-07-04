package com.tenth.nft.app.security;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.protobuf.Security;
import com.ruixi.tpulse.convention.routes.security.SecurityAuthRouteRequest;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.constants.RouterErrorCode;
import com.wallan.router.endpoint.core.security.HttpRouteMapping;
import com.wallan.router.endpoint.core.security.IClusterNodePathService;
import com.wallan.router.endpoint.core.security.IUserAuth;
import com.wallan.router.endpoint.core.utils.RouterExceptions;
import com.wallan.router.exception.BizException;
import com.wallan.router.vo.DebugResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shijie
 */
@Component
@ConditionalOnProperty(name = "router.security.open", havingValue = "true")
public class TpulseAuthFilter extends OncePerRequestFilter implements IUserAuth {

    private static final Logger LOGGER = LoggerFactory.getLogger(TpulseAuthFilter.class);

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private IClusterNodePathService clusterNodePathService;
    @Value("${router.debug: false}")
    private boolean debug;
    @Autowired
    private SimpleLockManger simpleLockManger;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        HttpRouteMapping clusterNodePath = clusterNodePathService.findOne(path);
        if(null == clusterNodePath) {
            filterChain.doFilter(request, response);
            return;
        }

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[httprequest]path: {}", path);
        }
        try{

            if(clusterNodePath.getUserAuth()){

                String uid = request.getHeader(TpulseHeaders.UID);
                String token = request.getHeader(TpulseHeaders.TOKEN);
//                if(LOGGER.isDebugEnabled()){
//                    LOGGER.debug("[httprequest]auth, uid: {}, token: {}", uid, token);
//                }
                if(Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(token)){
                    throw BizException.newInstance(RouterErrorCode.USER_AUTH_UNVALID);
                }

                routeClient.send(
                        Security.AUTH_IC.newBuilder().setUid(uid).setToken(token).build(),
                        SecurityAuthRouteRequest.class
                );

                //TPULSE_UID
                //TPULSE_TOKEN
                GameUserContext context = GameUserContext.get();
                context.getBuilder().setAttr(TpulseHeaders.UID, uid);
                context.getBuilder().setAttr(TpulseHeaders.TOKEN, token);
                if(debug){
                    context.getBuilder().setAttr(DebugResponse.HEADER_DEBUGABLE, "1");
                    context.getBuilder().setAttr(DebugResponse.HEADER_PATH, path);
                }

                SimpleLock lock = simpleLockManger.get(Long.valueOf(uid));
                lock.lock();
                try{
                    filterChain.doFilter(request, response);
                }finally {
                    lock.unlock();
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }catch (BizException e){
            LOGGER.warn("", e);
            RouterExceptions.flushExcepiton(request, response, RouterErrorCode.USER_AUTH_UNVALID);
        }

    }

    @Override
    public void auth(HttpServletRequest request, HttpRouteMapping clusterNodePath) {

    }

}
