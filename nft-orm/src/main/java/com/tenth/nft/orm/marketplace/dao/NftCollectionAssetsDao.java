package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftCollectionAssets;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/05 16:18
 */
@Component
public class NftCollectionAssetsDao extends SimpleDao<NftCollectionAssets> {

    public NftCollectionAssetsDao() {
        super(NftCollectionAssets.class);
    }
}