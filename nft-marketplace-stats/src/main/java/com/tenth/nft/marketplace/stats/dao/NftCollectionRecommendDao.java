package com.tenth.nft.marketplace.stats.dao;

import com.tenth.nft.marketplace.stats.dao.expression.NftCollectionRecommendQuery;
import com.tenth.nft.marketplace.stats.entity.NftCollectionRecommend;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class NftCollectionRecommendDao extends SimpleDao<NftCollectionRecommend> {

    public NftCollectionRecommendDao() {
        super(NftCollectionRecommend.class);
    }

    public void clearAllByVersion(String version) {
        remove(
                NftCollectionRecommendQuery.newBuilder()
                        .version(version)
                        .build()
        );
    }
}
