package com.tenth.nft.marketplace.common.service;

import com.tenth.nft.convention.NftIdModule;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.marketplace.common.dao.AbsNftBuyOrderDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderUpdate;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tenth.nft.marketplace.common.entity.AbsNftOrder;
import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tenth.nft.marketplace.common.vo.NftOrderStatusRequest;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author shijie
 */
public abstract class AbsNftOrderService<T extends AbsNftOrder> {

    @Autowired
    private GsCollectionIdService gsCollectionIdService;

    private AbsNftBuyOrderDao<T> nftBuyOrderDao;

    public AbsNftOrderService(AbsNftBuyOrderDao<T> nftBuyOrderDao) {
        this.nftBuyOrderDao = nftBuyOrderDao;
    }

    public T create(String buyer, NftOuterProduct product, AbsNftAssets nftAssets) {

        T nftOrder = newEntity();
        Long orderId = gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
        Long expiredAt = WalletTimes.getExpiredAt();
        nftOrder.setId(orderId);
        nftOrder.setSeller(product.getSeller());
        nftOrder.setBuyer(buyer);
        nftOrder.setAssetsId(nftAssets.getId());
        nftOrder.setOuterOrderId(product.getOuterOrderId());
        nftOrder.setCreatedAt(System.currentTimeMillis());
        nftOrder.setUpdatedAt(nftOrder.getCreatedAt());
        nftOrder.setCurrency(product.getCurrency());
        nftOrder.setPrice(new BigDecimal(product.getPrice()));
        nftOrder.setQuantity(product.getQuantity());
        nftOrder.setStatus(NftOrderStatus.CREATE);
        nftOrder.setExpiredAt(expiredAt);
        return nftBuyOrderDao.insert(nftOrder);
    }

    protected abstract T newEntity();

    public T findOne(Long assetsId, Long orderId) {
        return nftBuyOrderDao.findOne(
                AbsNftBuyOrderQuery.newBuilder().assetsId(assetsId).id(orderId).build()
        );
    }

    public void updateStatus(Long assetsId, Long orderId, NftOrderStatus status) {
        updateStatus(assetsId, orderId, status, null);
    }

    public void updateStatus(Long assetsId, Long orderId, NftOrderStatus status, String remark){
        nftBuyOrderDao.update(
                AbsNftBuyOrderQuery.newBuilder().assetsId(assetsId).id(orderId).build(),
                AbsNftBuyOrderUpdate.newBuilder().setStatus(status).remark(remark).build()
        );
    }

    public NftOrderStatus getStatus(Long assetsId, Long orderId){
        T order = nftBuyOrderDao.findOne(AbsNftBuyOrderQuery.newBuilder()
                .assetsId(assetsId)
                .id(orderId)
                .build());
        if(null != order){
            return order.getStatus();
        }
        return null;
    }

    public TxnStatus getStatus(NftOrderStatusRequest request) {

        NftOrderStatus status = getStatus(request.getAssetsId(), request.getOrderId());
        //Keep consistent with web3
        TxnStatus txnStatus = null;
        if(status == NftOrderStatus.CREATE){
            txnStatus = TxnStatus.PENDING;
        }else if(status == NftOrderStatus.COMPLETE){
            txnStatus = TxnStatus.SUCCESS;
        }else{
            txnStatus = TxnStatus.FAIL;
        }

        return txnStatus;

    }
}
