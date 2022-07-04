package com.tenth.nft.orm.external.dao;

import com.tenth.nft.orm.external.entity.ExternalNftCollection;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Component
public class NftCollectionNoCacheDao extends SimpleDao<ExternalNftCollection> {

    public NftCollectionNoCacheDao() {
        super(ExternalNftCollection.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}