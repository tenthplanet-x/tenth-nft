package com.tenth.nft.utils;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author shijie
 */
public class BigDecimalTest {

    @Test
    public void toBigDecimal(){

        BigDecimal bigDecimal = new BigDecimal("0.000000000000000001");
        System.out.println(bigDecimal.toString());
    }
}
