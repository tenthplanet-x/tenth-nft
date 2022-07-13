package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftBlockchain;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Component
public class NftBlockchainNoCacheDao extends SimpleDao<NftBlockchain> {

    public NftBlockchainNoCacheDao() {
        super(NftBlockchain.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}