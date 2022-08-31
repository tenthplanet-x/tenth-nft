package com.tenth.nft.exchange.web3.service;

import com.tenth.nft.convention.Web3Properties;
import com.tpulse.gs.convention.cypher.rsa.RSAUtils;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.wallan.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

/**
 * @author shijie
 */
public abstract class AbsSignService {

    @Autowired
    private Web3Properties web3Properties;

    protected String wrap(Object request) {

        try{
            String content = JsonUtil.toJson(request);
            String signature = Base64Utils.encode(RSAUtils.sign(content.getBytes(StandardCharsets.UTF_8), web3Properties.getRsa().getPrivateKey()));
            String wrappedStr = String.format("%s.%s", Base64Utils.encode(content.getBytes(StandardCharsets.UTF_8)), signature);
            return wrappedStr;
        }catch (Exception e){
            throw new RuntimeException("", e);
        }
    }

    protected <T> T unwrap(String token, Class<T> returnResponse) {
        String content = new String(Base64Utils.decode(token.substring(0, token.lastIndexOf("."))), StandardCharsets.UTF_8);
        return JsonUtil.fromJson(content, returnResponse);
    }
}
