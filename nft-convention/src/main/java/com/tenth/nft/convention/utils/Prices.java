package com.tenth.nft.convention.utils;

import java.text.DecimalFormat;

/**
 * @author shijie
 */
public class Prices {

    public static String toString(float price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#########");
        return decimalFormat.format(price);
    }
}
