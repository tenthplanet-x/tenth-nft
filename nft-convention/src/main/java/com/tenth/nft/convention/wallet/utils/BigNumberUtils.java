package com.tenth.nft.convention.wallet.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author shijie
 */
public class BigNumberUtils {

    public static final BigInteger BIG_NUMBER_MAX = BigInteger.valueOf(2).pow(256).subtract(BigInteger.ONE);

    public static String from(Float price) {
        return BigDecimal.valueOf(price).toString();
    }

    public static boolean gte(String current, String compareTo) {
        BigDecimal _current = new BigDecimal(current);
        BigDecimal _compareTo = new BigDecimal(compareTo);
        return _current.compareTo(_compareTo) >= 0;
    }

    public static String add(String current, String delta) {
        BigDecimal _current = new BigDecimal(current);
        BigDecimal _delta = new BigDecimal(delta);
        return _current.add(_delta).toString();
    }

    public static String minus(String current, String delta) {
        BigDecimal _current = new BigDecimal(current);
        BigDecimal _delta = new BigDecimal(delta);
        return _current.subtract(_delta).toString();
    }
}
