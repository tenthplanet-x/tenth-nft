package com.tenth.nft.crawler.dao;

import com.tenth.nft.crawler.entity.NftCollectionStats;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/15 14:07
 */
@Component
public class NftCollectionStatsDao extends SimpleDao<NftCollectionStats> {

    public NftCollectionStatsDao() {
        super(NftCollectionStats.class);
    }
}