package com.tenth.nft.marketplace.web3.dao;

import com.tenth.nft.marketplace.common.dao.AbsNftBelongDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftBelong;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3NftBelongDao extends AbsNftBelongDao<Web3NftBelong> {

    public Web3NftBelongDao() {
        super(Web3NftBelong.class);
    }
}
