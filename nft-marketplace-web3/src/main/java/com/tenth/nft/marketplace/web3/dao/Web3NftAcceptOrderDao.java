package com.tenth.nft.marketplace.web3.dao;

import com.tenth.nft.marketplace.common.dao.AbsNftBuyOrderDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftAcceptOrder;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3NftAcceptOrderDao extends AbsNftBuyOrderDao<Web3NftAcceptOrder> {

    public Web3NftAcceptOrderDao() {
        super(Web3NftAcceptOrder.class);
    }
}
