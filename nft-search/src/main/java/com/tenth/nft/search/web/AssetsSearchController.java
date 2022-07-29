package com.tenth.nft.search.web;

import com.tenth.nft.search.NftSearchPaths;
import com.tenth.nft.search.dto.AssetsDetailSearchDTO;
import com.tenth.nft.search.dto.AssetsOwnSearchDTO;
import com.tenth.nft.search.dto.AssetsSearchDTO;
import com.tenth.nft.search.service.AssetsSearchService;
import com.tenth.nft.search.vo.AssetsDetailSearchRequest;
import com.tenth.nft.search.vo.AssetsOwnSearchRequest;
import com.tenth.nft.search.vo.AssetsSearchRequest;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.annotation.Route;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@HttpRoute(userAuth = true)
@Route
public class AssetsSearchController {

    @Autowired
    private AssetsSearchService assetsSearchService;

    @RequestMapping(NftSearchPaths.ASSETS_LIST)
    public Response list(@RequestBody AssetsSearchRequest request){
        Validations.check(request);
        Page<AssetsSearchDTO> collections = assetsSearchService.list(request);
        return Response.successBuilder().data(collections).build();
    }

    @RequestMapping(NftSearchPaths.ASSETS)
    public Response assets(@RequestBody AssetsDetailSearchRequest request){
        Validations.check(request);
        AssetsDetailSearchDTO dto = assetsSearchService.detail(request);
        return Response.successBuilder().data(dto).build();
    }

    @RequestMapping(NftSearchPaths.ASSETS_OWN_LIST)
    public Response list(@RequestBody AssetsOwnSearchRequest request){
        Validations.check(request);
        Page<AssetsOwnSearchDTO> collections = assetsSearchService.list(request);
        return Response.successBuilder().data(collections).build();
    }

}
