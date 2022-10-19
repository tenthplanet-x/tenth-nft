package com.tenth.nft.convention;

import com.google.common.base.Joiner;

/**
 * @author shijie
 */
public class OssPaths {

    public static final String COLLECTION = "tpulse/collection";

    public static String create(String module, Object... cascadeNames) {
        String path = Joiner.on("_").join(cascadeNames);
        return String.format("%s/%s", module, path);
    }
}
