package com.tenth.nft.marketplace.stats.dao;

import com.tenth.nft.marketplace.stats.entity.NftAssetsVolumeStats;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 18:58
 */
@Component
public class NftAssetsVolumeStatsDao extends SimpleDao<NftAssetsVolumeStats> {

    public NftAssetsVolumeStatsDao() {
        super(NftAssetsVolumeStats.class);
    }
}