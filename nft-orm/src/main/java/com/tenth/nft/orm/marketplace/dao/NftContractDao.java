package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftContract;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
@Component
public class NftContractDao extends SimpleDao<NftContract> {

    public NftContractDao() {
        super(NftContract.class);
    }
}