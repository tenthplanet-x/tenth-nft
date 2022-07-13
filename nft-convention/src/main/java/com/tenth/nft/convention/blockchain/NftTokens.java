package com.tenth.nft.convention.blockchain;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author shijie
 */
public class NftTokens {

    private static final Logger LOGGER = LoggerFactory.getLogger(NftTokens.class);

    /**
     * generator a token of 40 characters
     * @return
     */
    public static String randomHexToken(){
        try {
            String plainText = UUID.randomUUID().toString();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA"); //sha-1
            byte[] cipherBytes = messageDigest.digest(plainText.getBytes());
            String cipherStr = Hex.encodeHexString(cipherBytes);
            return "0x" + cipherStr;
        }catch (Exception e){
            LOGGER.error("", e);
            throw new RuntimeException("", e);
        }
    }
}
