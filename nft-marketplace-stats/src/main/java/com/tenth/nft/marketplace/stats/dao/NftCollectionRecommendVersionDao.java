package com.tenth.nft.marketplace.stats.dao;

import com.tenth.nft.marketplace.stats.dao.expression.NftCollectionRecommendVersionQuery;
import com.tenth.nft.marketplace.stats.dao.expression.NftCollectionRecommendVersionUpdate;
import com.tenth.nft.marketplace.stats.entity.NftCollectionRecommendVersion;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 15:52
 */
@Component
public class NftCollectionRecommendVersionDao extends SimpleDao<NftCollectionRecommendVersion> {

    public static final Logger LOGGER = LoggerFactory.getLogger(NftCollectionRecommendVersionDao.class);

    private static final Long KEY = 1l;

    public NftCollectionRecommendVersionDao() {
        super(NftCollectionRecommendVersion.class);
    }

    public NftCollectionRecommendVersion findVersion() {
        return findOne(NftCollectionRecommendVersionQuery.newBuilder().id(KEY).build());
    }

    public void updateVersion(String newVersion) {
        findAndModify(
                NftCollectionRecommendVersionQuery.newBuilder().id(KEY).build(),
                NftCollectionRecommendVersionUpdate.newBuilder().setVersion(newVersion).build(),
                UpdateOptions.options().upsert(true)
        );
    }
}