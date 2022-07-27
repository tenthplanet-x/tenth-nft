package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftAssetsStats;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/27 15:26
 */
@Component
public class NftAssetsStatsNoCacheDao extends SimpleDao<NftAssetsStats> {

    public NftAssetsStatsNoCacheDao() {
        super(NftAssetsStats.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}