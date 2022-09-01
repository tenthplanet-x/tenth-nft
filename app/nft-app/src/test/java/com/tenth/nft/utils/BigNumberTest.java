package com.tenth.nft.utils;

import org.junit.Test;

import java.math.BigInteger;

/**
 * @author shijie
 */
public class BigNumberTest {

    @Test
    public void test(){
        BigInteger bigInteger = new BigInteger("F", 16);
        System.out.println(bigInteger.toString());
    }
}
