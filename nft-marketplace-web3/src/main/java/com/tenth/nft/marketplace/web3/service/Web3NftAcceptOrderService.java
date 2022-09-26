package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderUpdate;
import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tenth.nft.marketplace.common.service.AbsNftOrderService;
import com.tenth.nft.marketplace.web3.dao.Web3NftAcceptOrderDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftAcceptOrder;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class Web3NftAcceptOrderService extends AbsNftOrderService<Web3NftAcceptOrder> {

    private Web3NftAcceptOrderDao nftAcceptOrderDao;

    public Web3NftAcceptOrderService(Web3NftAcceptOrderDao nftAcceptOrderDao) {
        super(nftAcceptOrderDao);
        this.nftAcceptOrderDao = nftAcceptOrderDao;
    }

    @Override
    protected Web3NftAcceptOrder newEntity() {
        return new Web3NftAcceptOrder();
    }

    public void updateTxn(Long assetsId, Long orderId, String txn) {
        nftAcceptOrderDao.update(
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
