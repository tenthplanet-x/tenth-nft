package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.NftCategory;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Component
public class NftCategoryNoCacheDao extends SimpleDao<NftCategory> {

    public NftCategoryNoCacheDao() {
        super(NftCategory.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}