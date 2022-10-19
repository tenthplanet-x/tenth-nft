package com.tenth.nft.marketplace.common.dao;

import com.tenth.nft.marketplace.common.entity.AbsNftUbtLog;
import com.tpulse.gs.convention.dao.SimpleDao;

/**
 * @author shijie
 */
public abstract class AbsNftUbtLogDao<T extends AbsNftUbtLog> extends SimpleDao<T> {

    public AbsNftUbtLogDao(Class<T> collection) {
        super(collection);
    }
}
