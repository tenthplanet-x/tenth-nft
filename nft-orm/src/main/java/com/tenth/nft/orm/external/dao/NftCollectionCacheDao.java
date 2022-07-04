package com.tenth.nft.orm.external.dao;

import com.tenth.nft.orm.external.entity.ExternalNftCategory;
import com.tenth.nft.orm.external.entity.ExternalNftCollection;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Component
public class NftCollectionCacheDao extends SimpleDao<ExternalNftCollection> {

    public NftCollectionCacheDao() {
        super(ExternalNftCollection.class);
    }

    public void clearCache(Long collectionId) {
        IDaoOperator<ExternalNftCategory> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<ExternalNftCategory>) daoOperator).clearCache(collectionId);
        }
    }
}