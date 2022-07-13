package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@Component
public class NftAssetsDao extends SimpleDao<NftAssets> {

    public NftAssetsDao() {
        super(NftAssets.class);
    }

    public void clearCache(Long assetsId) {
        IDaoOperator<NftAssets> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<NftAssets>) daoOperator).clearCache(assetsId);
        }
    }
}