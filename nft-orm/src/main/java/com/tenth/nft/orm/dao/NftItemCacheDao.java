package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.ExternalNftAssets;
import com.tenth.nft.orm.entity.ExternalNftCategory;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Component
public class NftItemCacheDao extends SimpleDao<ExternalNftAssets> {

    public NftItemCacheDao() {
        super(ExternalNftAssets.class);
    }

    public void clearCache(String contractAddress) {
        IDaoOperator<ExternalNftCategory> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<ExternalNftCategory>) daoOperator).clearCache(contractAddress);
        }
    }
}