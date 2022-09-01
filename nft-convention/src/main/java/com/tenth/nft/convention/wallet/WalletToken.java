package com.tenth.nft.convention.wallet;

import com.google.common.base.Joiner;
import com.tpulse.gs.convention.cypher.rsa.RSAUtils;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.wallan.json.JsonUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
public class WalletToken {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalletToken.class);

    private WalletOrderBizContent bizContent;

    private String sign;

    protected void setBizContent(WalletOrderBizContent bizContent) {
        this.bizContent = bizContent;
    }

    protected void setSign(String sign) {
        this.sign = sign;
    }

    public static WalletToken decode(String token) {

        String json = new String(Base64Utils.decode(token), StandardCharsets.UTF_8);
        Map<String, Object> params = JsonUtil.fromJson(json, Map.class);
        String sign = MapUtils.getString(params, "sign");

        String contentJson = MapUtils.getString(params, "content");
        WalletOrderBizContent bizContent = JsonUtil.fromJson(contentJson, WalletOrderBizContent.class);

        WalletToken walletToken = new WalletToken();
        walletToken.bizContent = bizContent;
        walletToken.sign = sign;

        return walletToken;
    }

    public WalletOrderBizContent getBizContent() {
        return bizContent;
    }

    public boolean verify(String publicKey) {

        String contentString = JsonUtil.toJson(bizContent);
        Map<String, Object> params = new TreeMap<>();
        params.putAll(JsonUtil.fromJson(contentString, Map.class));
        String queryString = Joiner.on("&").join(params.entrySet().stream().map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())).collect(Collectors.toList()));
        try {
            return RSAUtils.verify(queryString.getBytes(StandardCharsets.UTF_8), publicKey, sign);
        }catch (Exception e){
            LOGGER.error("", e);
            return false;
        }

    }
}
