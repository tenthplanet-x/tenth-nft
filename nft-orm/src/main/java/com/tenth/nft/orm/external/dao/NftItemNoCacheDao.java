package com.tenth.nft.orm.external.dao;

import com.tenth.nft.orm.external.entity.ExternalNftAssets;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Component
public class NftItemNoCacheDao extends SimpleDao<ExternalNftAssets> {

    public NftItemNoCacheDao() {
        super(ExternalNftAssets.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}