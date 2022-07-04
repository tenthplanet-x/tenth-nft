package com.tenth.nft.orm.external.dao;

import com.tenth.nft.orm.external.entity.ExternalNftCategory;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Component
public class NftCategoryNoCacheDao extends SimpleDao<ExternalNftCategory> {

    public NftCategoryNoCacheDao() {
        super(ExternalNftCategory.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}