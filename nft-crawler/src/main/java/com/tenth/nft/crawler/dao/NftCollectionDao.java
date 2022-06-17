package com.tenth.nft.crawler.dao;

import com.tenth.nft.crawler.entity.NftCollection;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Component
public class NftCollectionDao extends SimpleDao<NftCollection> {

    public NftCollectionDao() {
        super(NftCollection.class);
    }
}