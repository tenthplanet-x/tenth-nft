package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.CurrencyRate;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
@Component
public class CurrencyRateDao extends SimpleDao<CurrencyRate> {

    public CurrencyRateDao() {
        super(CurrencyRate.class);
    }

    public void clearCache(String token) {
        IDaoOperator<CurrencyRate> daoOperator = getDaoOperator();
        if(daoOperator instanceof CachebaleDaoOperatorV2){
            ((CachebaleDaoOperatorV2<CurrencyRate>) daoOperator).clearCache(token);
        }
    }
}