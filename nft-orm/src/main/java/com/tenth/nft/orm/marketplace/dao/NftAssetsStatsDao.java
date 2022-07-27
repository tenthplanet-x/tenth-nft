package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftAssetsStats;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/27 15:26
 */
@Component
public class NftAssetsStatsDao extends SimpleDao<NftAssetsStats> {

    public NftAssetsStatsDao() {
        super(NftAssetsStats.class);
    }
}