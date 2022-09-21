package com.tenth.nft.marketplace.common.service;

import com.tenth.nft.convention.NftIdModule;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.marketplace.common.dao.AbsNftBuyOrderDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderUpdate;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tenth.nft.marketplace.common.entity.AbsNftBuyOrder;
import com.tenth.nft.marketplace.common.entity.AbsNftListing;
import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shijie
 */
public abstract class AbsNftBuyOrderService<T extends AbsNftBuyOrder> {

    @Autowired
    private GsCollectionIdService gsCollectionIdService;

    private AbsNftBuyOrderDao<T> nftBuyOrderDao;

    public AbsNftBuyOrderService(AbsNftBuyOrderDao<T> nftBuyOrderDao) {
        this.nftBuyOrderDao = nftBuyOrderDao;
    }

    public T create(String buyer, AbsNftListing nftListing, AbsNftAssets nftAssets) {

        T nftOrder = newEntity();
        Long orderId = gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
        Long expiredAt = WalletTimes.getExpiredAt();
        nftOrder.setId(orderId);
        nftOrder.setSeller(nftListing.getSeller());
        nftOrder.setBuyer(buyer);
        nftOrder.setAssetsId(nftAssets.getId());
        nftOrder.setListingId(nftListing.getId());
        nftOrder.setCreatedAt(System.currentTimeMillis());
        nftOrder.setUpdatedAt(nftOrder.getCreatedAt());
        nftOrder.setCurrency(nftListing.getCurrency());
        nftOrder.setPrice(nftListing.getPrice());
        nftOrder.setQuantity(nftListing.getQuantity());
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
}
