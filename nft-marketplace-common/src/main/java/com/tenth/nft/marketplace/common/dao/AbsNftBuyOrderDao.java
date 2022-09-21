package com.tenth.nft.marketplace.common.dao;

import com.tenth.nft.marketplace.common.entity.AbsNftBuyOrder;
import com.tpulse.gs.convention.dao.SimpleDao;

/**
 * @author shijie
 */
public class AbsNftBuyOrderDao<T extends AbsNftBuyOrder> extends SimpleDao<T> {

    public AbsNftBuyOrderDao(Class<T> collection) {
        super(collection);
    }

}
