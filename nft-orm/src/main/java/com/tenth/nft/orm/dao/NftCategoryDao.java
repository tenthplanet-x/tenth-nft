package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.NftCategory;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Component
public class NftCategoryDao extends SimpleDao<NftCategory> {

    public NftCategoryDao() {
        super(NftCategory.class);
    }

    public void clearCache(Integer version) {
        IDaoOperator<NftCategory> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<NftCategory>) daoOperator).clearCache(version);
        }
    }
}