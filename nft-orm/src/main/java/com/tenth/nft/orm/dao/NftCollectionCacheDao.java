package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.NftCollection;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Component
public class NftCollectionCacheDao extends SimpleDao<NftCollection> {

    public NftCollectionCacheDao() {
        super(NftCollection.class);
    }

}