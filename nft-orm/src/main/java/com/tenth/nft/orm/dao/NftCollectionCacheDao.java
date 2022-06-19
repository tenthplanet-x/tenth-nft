package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.NftCategory;
import com.tenth.nft.orm.entity.NftCollection;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Component
public class NftCollectionCacheDao extends SimpleDao<NftCollection> {

    public NftCollectionCacheDao() {
        super(NftCollection.class);
    }

    public void clearCache(Long collectionId) {
        IDaoOperator<NftCategory> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<NftCategory>) daoOperator).clearCache(collectionId);
        }
    }
}