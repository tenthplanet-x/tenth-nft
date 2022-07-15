package com.tenth.nft.convention.utils;

import java.text.DecimalFormat;

/**
 * @author shijie
 */
public class Prices {

    public static String toString(float price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#############");
        Double wrap = Double.valueOf(String.valueOf(price));
        return decimalFormat.format(wrap);
    }
}
