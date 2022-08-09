package com.tenth.nft.convention.wallet.utils;

/**
 * @author shijie
 */
public class WalletTimes {

    private static final Long DURATION = 5 * 60 * 60l;

    /**
     * Get the expired time start from now
     * @return
     */
    public static Long getExpiredAt() {
        return System.currentTimeMillis() + DURATION;
    }
}
