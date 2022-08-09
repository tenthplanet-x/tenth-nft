package com.tenth.nft.exchange.wallet;

import com.google.common.base.Joiner;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.WalletPayChannel;
import com.tpulse.gs.convention.cypher.rsa.RsaCypher;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.wallan.json.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Component
public class DefaultWalletProvider implements IWalletProvider{

    @Value("${wallet.blockchain}")
    private String blockchain;
    @Value("${wallet.rsa.private-key}")
    private String rsaPrivateKey;

    private RsaCypher rsaCypher;

    public DefaultWalletProvider(){
        try{
            rsaCypher = new RsaCypher(null, rsaPrivateKey);
        }catch (Exception e){
            throw new RuntimeException("", e);
        }
    }

    @Override
    public String createToken(WalletOrderBizContent bizContent) {

        String contentString = JsonUtil.toJson(bizContent);
        Map<String, Object> params = new TreeMap<>();
        params.putAll(JsonUtil.fromJson(contentString, Map.class));
        String queryString = Joiner.on("&").join(params.entrySet().stream().map(entry -> Joiner.on("=")).collect(Collectors.toList()));
        String sign = null;
        try {
            sign = rsaCypher.signBase64(queryString.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            throw new RuntimeException("", e);
        }

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("content", contentString);
        tokenMap.put("sign", sign);
        return Base64Utils.encode(JsonUtil.toJson(tokenMap).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String getChannel() {
        return WalletPayChannel.INSIDE.name();
    }

    @Override
    public String getBlockchain() {
        return blockchain;
    }
}
