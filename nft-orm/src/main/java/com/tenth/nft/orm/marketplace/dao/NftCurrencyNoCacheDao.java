package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftCurrency;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Component
public class NftCurrencyNoCacheDao extends SimpleDao<NftCurrency> {

    public NftCurrencyNoCacheDao() {
        super(NftCurrency.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}