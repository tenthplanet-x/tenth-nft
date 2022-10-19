package com.tenth.nft.marketplace.stats.dao;

import com.tenth.nft.marketplace.stats.entity.NftAssetsTaskProgress;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 16:25
 */
@Component
public class NftAssetsTaskProgressDao extends SimpleDao<NftAssetsTaskProgress> {

    public NftAssetsTaskProgressDao() {
        super(NftAssetsTaskProgress.class);
    }
}