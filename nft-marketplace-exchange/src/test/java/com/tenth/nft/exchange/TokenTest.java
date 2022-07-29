package com.tenth.nft.exchange;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author shijie
 */
public class TokenTest {

    @Test
    public void test() throws Exception{

        String plainText = UUID.randomUUID().toString();
        // 获取指定摘要算法的messageDigest对象
        MessageDigest messageDigest = MessageDigest.getInstance("SHA"); // 此处的sha代表sha1
        // 调用digest方法，进行加密操作
        byte[] cipherBytes = messageDigest.digest(plainText.getBytes());
        String cipherStr = Hex.encodeHexString(cipherBytes);
        System.out.println(cipherStr);

    }

}
