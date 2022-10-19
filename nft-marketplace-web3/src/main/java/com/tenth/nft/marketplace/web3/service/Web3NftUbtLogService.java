package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.marketplace.common.service.AbsNftUbtLogService;
import com.tenth.nft.marketplace.web3.dao.Web3NftUbtLogDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftUbtLog;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class Web3NftUbtLogService extends AbsNftUbtLogService<Web3NftUbtLog> {

    public Web3NftUbtLogService(Web3NftUbtLogDao nftUbtLogDao) {
        super(nftUbtLogDao);
    }

    @Override
    protected Web3NftUbtLog newNftUbtLog() {
        return new Web3NftUbtLog();
    }
}
