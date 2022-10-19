package com.tenth.nft.marketplace.web3.dao;

import com.tenth.nft.marketplace.common.dao.AbsNftAssetsDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftAssets;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3NftAssetsDao extends AbsNftAssetsDao<Web3NftAssets> {

    public Web3NftAssetsDao() {
        super(Web3NftAssets.class);
    }
}
