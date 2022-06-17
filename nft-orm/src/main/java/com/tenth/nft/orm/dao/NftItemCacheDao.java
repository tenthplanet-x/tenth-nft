package com.tenth.nft.orm.dao;

import com.tenth.nft.orm.entity.NftItem;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Component
public class NftItemCacheDao extends SimpleDao<NftItem> {

    public NftItemCacheDao() {
        super(NftItem.class);
    }
}