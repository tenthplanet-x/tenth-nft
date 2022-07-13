package com.tenth.nft.orm.external.dao;

import com.tenth.nft.orm.external.entity.ExternalNftCategory;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Component
public class ExternalNftCategoryDao extends SimpleDao<ExternalNftCategory> {

    public ExternalNftCategoryDao() {
        super(ExternalNftCategory.class);
    }

    public void clearCache(Integer version) {
        IDaoOperator<ExternalNftCategory> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<ExternalNftCategory>) daoOperator).clearCache(version);
        }
    }
}