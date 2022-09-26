package com.tenth.nft.marketplace.common.dao;

import com.tenth.nft.marketplace.common.entity.AbsNftOffer;
import com.tpulse.gs.convention.dao.SimpleDao;

/**
 * @author shijie
 */
public class AbsNftOfferDao<T extends AbsNftOffer> extends SimpleDao<T> {

    public AbsNftOfferDao(Class<T> collection) {
        super(collection);
    }
}
