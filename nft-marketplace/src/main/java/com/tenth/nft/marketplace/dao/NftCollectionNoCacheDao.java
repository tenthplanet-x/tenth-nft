package com.tenth.nft.marketplace.dao;

import com.tenth.nft.marketplace.entity.NftCollection;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@Component
public class NftCollectionNoCacheDao extends SimpleDao<NftCollection> {

    public NftCollectionNoCacheDao() {
        super(NftCollection.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}