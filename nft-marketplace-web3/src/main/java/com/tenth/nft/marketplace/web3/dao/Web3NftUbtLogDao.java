package com.tenth.nft.marketplace.web3.dao;

import com.tenth.nft.marketplace.common.dao.AbsNftUbtLogDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftUbtLog;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3NftUbtLogDao extends AbsNftUbtLogDao<Web3NftUbtLog> {

    public Web3NftUbtLogDao() {
        super(Web3NftUbtLog.class);
    }

}
