package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftActivity;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
@Component
public class NftActivityNoCacheDao extends SimpleDao<NftActivity> {

    public NftActivityNoCacheDao() {
        super(NftActivity.class);
    }
}