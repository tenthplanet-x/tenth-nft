package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.NftCategory;
import com.tenth.nft.orm.entity.NftItem;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Component
public class NftItemCacheDao extends SimpleDao<NftItem> {

    public NftItemCacheDao() {
        super(NftItem.class);
    }

    public void clearCache(String contractAddress) {
        IDaoOperator<NftCategory> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<NftCategory>) daoOperator).clearCache(contractAddress);
        }
    }
}