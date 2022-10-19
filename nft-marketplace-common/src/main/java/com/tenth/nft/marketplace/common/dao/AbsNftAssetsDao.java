package com.tenth.nft.marketplace.common.dao;

import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tpulse.gs.convention.dao.SimpleDao;

/**
 * @author shijie
 */
public abstract class AbsNftAssetsDao<T extends AbsNftAssets> extends SimpleDao<T> {

    public AbsNftAssetsDao(Class<T> collection) {
        super(collection);
    }
}
