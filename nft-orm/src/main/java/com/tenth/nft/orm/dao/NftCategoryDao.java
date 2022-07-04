package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.ExternalNftCategory;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Component
public class NftCategoryDao extends SimpleDao<ExternalNftCategory> {

    public NftCategoryDao() {
        super(ExternalNftCategory.class);
    }

    public void clearCache(Integer version) {
        IDaoOperator<ExternalNftCategory> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<ExternalNftCategory>) daoOperator).clearCache(version);
        }
    }
}