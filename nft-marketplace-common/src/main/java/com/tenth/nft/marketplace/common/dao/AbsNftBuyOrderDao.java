package com.tenth.nft.marketplace.common.dao;

import com.tenth.nft.marketplace.common.entity.AbsNftOrder;
import com.tpulse.gs.convention.dao.SimpleDao;

/**
 * @author shijie
 */
public class AbsNftBuyOrderDao<T extends AbsNftOrder> extends SimpleDao<T> {

    public AbsNftBuyOrderDao(Class<T> collection) {
        super(collection);
    }

}
