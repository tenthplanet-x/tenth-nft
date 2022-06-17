package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.NftItem;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Component
public class NftItemNoCacheDao extends SimpleDao<NftItem> {

    public NftItemNoCacheDao() {
        super(NftItem.class);
    }

    @Override
    protected Optional<Boolean> cacheable() {
        return Optional.of(false);
    }
}