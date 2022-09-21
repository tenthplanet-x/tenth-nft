package com.tenth.nft.marketplace.common.dao;

import com.tenth.nft.marketplace.common.entity.AbsNftListing;
import com.tpulse.gs.convention.dao.SimpleDao;

/**
 * @author shijie
 */
public class AbsNftListingDao<T extends AbsNftListing> extends SimpleDao<T> {

    public AbsNftListingDao(Class<T> collection) {
        super(collection);
    }

}
