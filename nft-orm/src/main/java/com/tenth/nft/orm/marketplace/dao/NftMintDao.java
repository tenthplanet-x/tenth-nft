package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftMint;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
@Component
public class NftMintDao extends SimpleDao<NftMint> {

    public NftMintDao() {
        super(NftMint.class);
    }
}