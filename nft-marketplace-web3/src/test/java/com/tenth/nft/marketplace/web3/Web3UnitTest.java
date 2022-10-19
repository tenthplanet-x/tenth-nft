package com.tenth.nft.marketplace.web3;

import org.junit.Test;
import org.web3j.utils.Convert;

/**
 * @author shijie
 */
public class Web3UnitTest {

    @Test
    public void test(){

        String str = Convert.toWei("0.001", Convert.Unit.ETHER).toBigInteger().toString();
        System.out.println(str);
    }
}
