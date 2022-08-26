package com.tenth.nft.convention.web3.utils;

import java.math.BigInteger;

/**
 * @author shijie
 */
public class HexAddresses {

    public static String of(Long id) {
        return BigInteger.valueOf(id).toString(16);
    }
}
