package com.tenth.nft.marketplace.common.dao;

import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tpulse.gs.convention.dao.SimpleDao;

/**
 * @author shijie
 */
public abstract class AbsNftCollectionDao<T extends AbsNftCollection> extends SimpleDao<T> {

    public AbsNftCollectionDao(Class<T> collection) {
        super(collection);
    }
}
