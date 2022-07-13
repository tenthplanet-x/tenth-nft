package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@Component
public class NftCollectionDao extends SimpleDao<NftCollection> {

    public NftCollectionDao() {
        super(NftCollection.class);
    }

    public void clearCache(Long collectionId) {
        IDaoOperator<NftCollection> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<NftCollection>) daoOperator).clearCache(collectionId);
        }
    }
}