package com.tenth.nft.crawler.dao;

import com.tenth.nft.crawler.entity.ExternalNftCollectionStats;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/15 14:07
 */
@Component
public class ExternalNftCollectionStatsDao extends SimpleDao<ExternalNftCollectionStats> {

    public ExternalNftCollectionStatsDao() {
        super(ExternalNftCollectionStats.class);
    }
}