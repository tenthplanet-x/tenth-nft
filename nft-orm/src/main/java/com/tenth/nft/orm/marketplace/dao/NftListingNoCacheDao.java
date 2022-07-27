package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
@Component
public class NftListingNoCacheDao extends SimpleDao<NftListing> {

    public NftListingNoCacheDao() {
        super(NftListing.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}