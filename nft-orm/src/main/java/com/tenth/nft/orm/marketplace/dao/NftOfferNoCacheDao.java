package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftOffer;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/18 08:24
 */
@Component
public class NftOfferNoCacheDao extends SimpleDao<NftOffer> {

    public NftOfferNoCacheDao() {
        super(NftOffer.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}