package com.tenth.nft.convention;

/**
 * @author shijie
 * @createdAt 2022/6/15 09:35
 */
public enum TenthOssKeys {

    nft;

    public static String join(TenthOssKeys workspace, String filename) {
        return workspace.name() + "/" + filename + "_" + System.currentTimeMillis();
    }
}
