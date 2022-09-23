package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderUpdate;
import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tenth.nft.marketplace.common.service.AbsNftBuyOrderService;
import com.tenth.nft.marketplace.web3.dao.Web3NftBuyOrderDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftBuyOrder;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class Web3NftBuyOrderService extends AbsNftBuyOrderService<Web3NftBuyOrder> {

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
                AbsNftBuyOrderUpdate.newBuilder()
                        .setStatus(NftOrderStatus.COMPLETE)
                        .txn(txn)
                        .build()
        );
    }
}
