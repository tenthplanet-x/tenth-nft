package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.CurrencyRate;
import com.tpulse.gs.convention.dao.IDaoOperator;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.dao.cache.CachebaleDaoOperatorV2;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
@Component
public class CurrencyRateNoCacheDao extends SimpleDao<CurrencyRate> {

    public CurrencyRateNoCacheDao() {
        super(CurrencyRate.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}