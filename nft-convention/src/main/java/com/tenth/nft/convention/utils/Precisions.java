package com.tenth.nft.convention.utils;

import org.web3j.abi.datatypes.Int;

/**
 * @author shijie
 */
public class Precisions {

    private static final Integer CREATOR_FEE_RATE_PRECISION = 4;

    /**
     *
     * @param creatorFeeRate %
     * @return
     */
    public static Integer toCreatorFeeRate(String creatorFeeRate) {
        return (int)(Float.valueOf(creatorFeeRate) / 100f * Math.pow(10, CREATOR_FEE_RATE_PRECISION));
    }

    public static Integer getCreatorFeeRatePrecision() {
        return CREATOR_FEE_RATE_PRECISION;
    }
}
