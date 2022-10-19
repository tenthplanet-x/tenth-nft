package com.tenth.nft.convention;

/**
 * @author shijie
 */
public class UnionIds {

    public static final String CHANNEL_BUILDIN = "buildin";
    public static final String CHANNEL_WEB3 = "web3";

    public static String wrap(String channel, Long id) {
        return String.format("%s_%s", channel, id);
    }
}
