package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderUpdate;
import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tenth.nft.marketplace.common.service.AbsNftOrderService;
import com.tenth.nft.marketplace.web3.dao.Web3NftBuyOrderDao;
import com.tenth.nft.marketplace.web3.dao.expression.Web3NftBuyOrderUpdate;
import com.tenth.nft.marketplace.web3.entity.Web3NftBuyOrder;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class Web3NftBuyOrderService extends AbsNftOrderService<Web3NftBuyOrder> {

    private Web3NftBuyOrderDao nftBuyOrderDao;

    public Web3NftBuyOrderService(Web3NftBuyOrderDao nftBuyOrderDao) {
        super(nftBuyOrderDao);
        this.nftBuyOrderDao = nftBuyOrderDao;
    }

    @Override
    protected Web3NftBuyOrder newEntity() {
        return new Web3NftBuyOrder();
    }

    public void updateTxn(Long assetsId, Long orderId, String txn) {
        nftBuyOrderDao.update(
                AbsNftBuyOrderQuery.newBuilder()
                        .assetsId(assetsId)
                        .id(orderId)
                        .build(),
                Web3NftBuyOrderUpdate.newWeb3Builder()
                        .txn(txn)
                        .setStatus(NftOrderStatus.COMPLETE)
                        .build()
        );
    }
}
