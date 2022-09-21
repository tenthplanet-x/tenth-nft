package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.marketplace.common.service.AbsNftBelongService;
import com.tenth.nft.marketplace.web3.dao.Web3NftBelongDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftBelong;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class Web3NftBelongService extends AbsNftBelongService<Web3NftBelong> {

    public Web3NftBelongService(Web3NftBelongDao nftBelongDao) {
        super(nftBelongDao);
    }

    @Override
    protected void afterQuantityChange(Web3NftBelong nftBelong) {

    }
}
