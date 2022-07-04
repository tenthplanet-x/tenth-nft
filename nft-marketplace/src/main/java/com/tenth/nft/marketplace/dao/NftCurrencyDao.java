package com.tenth.nft.marketplace.dao;

import com.tenth.nft.marketplace.entity.NftCurrency;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Component
public class NftCurrencyDao extends SimpleDao<NftCurrency> {

    public NftCurrencyDao() {
        super(NftCurrency.class);
    }

    public void clearCache(String blockchain) {
        IDaoOperator<NftCurrency> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<NftCurrency>) daoOperator).clearCache(blockchain);
        }
    }
}