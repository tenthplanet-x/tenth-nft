package com.tenth.nft.marketplace.web3.dao;

import com.tenth.nft.marketplace.common.dao.AbsNftBuyOrderDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftBuyOrder;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3NftBuyOrderDao extends AbsNftBuyOrderDao<Web3NftBuyOrder> {

    public Web3NftBuyOrderDao() {
        super(Web3NftBuyOrder.class);
    }


}
