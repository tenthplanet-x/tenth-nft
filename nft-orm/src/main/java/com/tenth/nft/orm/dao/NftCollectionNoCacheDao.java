package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.NftCollection;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
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