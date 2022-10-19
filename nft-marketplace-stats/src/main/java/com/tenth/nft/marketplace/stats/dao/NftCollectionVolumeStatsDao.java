package com.tenth.nft.marketplace.stats.dao;

import com.tenth.nft.marketplace.stats.entity.NftCollectionVolumeStats;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 18:58
 */
@Component
public class NftCollectionVolumeStatsDao extends SimpleDao<NftCollectionVolumeStats> {

    public NftCollectionVolumeStatsDao() {
        super(NftCollectionVolumeStats.class);
    }
}