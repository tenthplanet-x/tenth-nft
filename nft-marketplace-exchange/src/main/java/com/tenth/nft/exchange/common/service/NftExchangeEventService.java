package com.tenth.nft.exchange.common.service;

import com.tenth.nft.convention.blockchain.NullAddress;
import com.tenth.nft.orm.marketplace.dao.NftActivityDao;
import com.tenth.nft.orm.marketplace.entity.NftActivity;
import com.tenth.nft.orm.marketplace.entity.NftActivityEventType;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.orm.marketplace.entity.NftOrder;
import com.tenth.nft.orm.marketplace.entity.event.*;
import com.tenth.nft.protobuf.NftExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class NftExchangeEventService {

    @Autowired
    private NftActivityDao nftActivityDao;

    public void sendMintEvent(NftExchange.MINT_IC request) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(request.getAssetsId());
        activity.setType(NftActivityEventType.Minted);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        MintEvent mintEvent = new MintEvent();
        mintEvent.setFrom(NullAddress.TOKEN);
        mintEvent.setTo(request.getOwner());
        mintEvent.setQuantity(request.getQuantity());
        activity.setMint(mintEvent);;

        nftActivityDao.insert(activity);
    }

    public Long sendListingEvent(NftListing listing) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(listing.getAssetsId());
        activity.setType(NftActivityEventType.List);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        ListEvent listEvent = new ListEvent();
        listEvent.setFrom(listing.getUid());
        listEvent.setFromAddress(listing.getUidAddress());
        listEvent.setPrice(listing.getPrice());
        listEvent.setQuantity(listing.getQuantity());
        listEvent.setCurrency(listing.getCurrency());
        listEvent.setExpireAt(listing.getExpireAt());
        activity.setList(listEvent);

        return nftActivityDao.insert(activity).getId();

    }

    public void sendTransferEvent(NftOrder nftOrder) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(nftOrder.getAssetsId());
        activity.setType(NftActivityEventType.Transfer);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        TransferEvent transfer = new TransferEvent();
        transfer.setFrom(nftOrder.getOwner());
        transfer.setTo(nftOrder.getBuyer());
        transfer.setQuantity(nftOrder.getQuantity());
        transfer.setPrice(nftOrder.getPrice());
        transfer.setCurrency(nftOrder.getCurrency());

        activity.setTransfer(transfer);

        nftActivityDao.insert(activity);

    }

    public void sendSaleEvent(NftOrder nftOrder) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(nftOrder.getAssetsId());
        activity.setType(NftActivityEventType.Sale);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        SaleEvent sale = new SaleEvent();
        sale.setFrom(nftOrder.getOwner());
        sale.setTo(nftOrder.getBuyer());
        sale.setQuantity(nftOrder.getQuantity());
        sale.setPrice(nftOrder.getPrice());
        sale.setCurrency(nftOrder.getCurrency());
        activity.setSale(sale);

        nftActivityDao.insert(activity);

    }

    public void sendCancelEvent(NftListing nftListing, String reason) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(nftListing.getAssetsId());
        activity.setType(NftActivityEventType.Cancel);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        ListCancelEvent list = new ListCancelEvent();
        list.setFrom(nftListing.getUid());
        list.setQuantity(nftListing.getQuantity());
        list.setPrice(nftListing.getPrice());
        list.setCurrency(nftListing.getCurrency());
        list.setReason(reason);
        activity.setCancel(list);

        nftActivityDao.insert(activity);
    }


}
