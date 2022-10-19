package com.tenth.nft.marketplace.common.dao;

import com.tenth.nft.marketplace.common.entity.AbsNftBelong;
import com.tpulse.gs.convention.dao.SimpleDao;

/**
 * @author shijie
 */
public class AbsNftBelongDao<T extends AbsNftBelong> extends SimpleDao<T> {

    public AbsNftBelongDao(Class<T> collection) {
        super(collection);
    }
}
