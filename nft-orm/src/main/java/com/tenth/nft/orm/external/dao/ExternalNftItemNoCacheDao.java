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
public class ExternalNftItemNoCacheDao extends SimpleDao<ExternalNftAssets> {

    public ExternalNftItemNoCacheDao() {
        super(ExternalNftAssets.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}