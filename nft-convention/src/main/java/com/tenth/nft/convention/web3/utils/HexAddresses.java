package com.tenth.nft.convention.web3.utils;

import com.google.common.base.Strings;

import java.math.BigInteger;

/**
 * @author shijie
 */
public class HexAddresses {

    public static String of(Long id) {
        String hex = BigInteger.valueOf(id).toString(16);
        return String.format("0x%s", Strings.padStart(hex, 40, '0'));
    }


}
