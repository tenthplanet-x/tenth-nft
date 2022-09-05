package com.tenth.nft.convention.web3.sign;

import com.tpulse.gs.convention.cypher.rsa.RSAUtils;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.wallan.json.JsonUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author shijie
 */
public class StructContentHash {

    public static String wrap(String content, String privateKey) throws Exception {
        String encodedContent = sign(content, privateKey);
        return wrapToToken(encodedContent, privateKey);
    }


    private static String sign(String content, String privateKey) throws Exception {
        return Base64Utils.encode(RSAUtils.sign(content.getBytes(StandardCharsets.UTF_8), privateKey));
    }

    private static String wrapToToken(String content, String signature) {
        String wrappedStr = String.format("%s.%s", Base64Utils.encode(content.getBytes(StandardCharsets.UTF_8)), signature);
        return wrappedStr;
    }

    public static String unwrap(String wrappedContent) {

        String encodedContent = wrappedContent.substring(0, wrappedContent.lastIndexOf("."));
        String signature = wrappedContent.substring(wrappedContent.lastIndexOf("."));

        String content = new String(Base64Utils.decode(encodedContent), StandardCharsets.UTF_8);
        return content;
    }


}
