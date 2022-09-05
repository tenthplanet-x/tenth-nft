package com.tenth.nft.exchange.common.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.blockchain.NullAddress;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.exchange.buildin.controller.vo.NftActivityListRequest;
import com.tenth.nft.exchange.buildin.dto.NftActivityDTO;
import com.tenth.nft.orm.marketplace.dao.NftActivityDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftActivityQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftActivityUpdate;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.orm.marketplace.entity.event.*;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class NftActivityService {

    @Autowired
    private RouteClient routeClient;

    @Autowired
    private NftActivityDao nftActivityDao;

    public Page<NftActivityDTO> list(NftActivityListRequest request) {

        Page<NftActivity> dataPage = nftActivityDao.findPage(
                NftActivityQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .event(null != request.getEvent()? request.getEvent().name(): null)
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField("_id")
                        .setReverse(true)
                        .build()
        );

        List<NftActivityDTO> dtos = dataPage.getData().stream().map(NftActivityDTO::from).collect(Collectors.toList());


        //fill with userProfile
        Set<Long> fromUids = dtos.stream().map(dto -> dto.getFrom()).filter(Objects::nonNull).filter(from -> !NullAddress.TOKEN.equals(from)).collect(Collectors.toSet());
        Set<Long> toUids = dtos.stream().map(dto -> dto.getTo()).filter(Objects::nonNull).filter(from -> !NullAddress.TOKEN.equals(from)).collect(Collectors.toSet());
        fromUids.addAll(toUids);
        Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(fromUids).build(),
                SearchUserProfileRouteRequest.class
        ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
        dtos.stream().forEach(dto -> {
            if(null != dto.getFrom()){
                dto.setFromProfile(userProfileDTOMap.get(dto.getFrom()));
            }
            if(null != dto.getTo()){
                dto.setToProfile(userProfileDTOMap.get(dto.getTo()));
            }
        });

        return new Page<>(0, dtos);
    }

    public void sendMintEvent(NftAssets assets) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(assets.getId());
        activity.setType(NftActivityEventType.Minted);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        MintEvent mintEvent = new MintEvent();
        mintEvent.setFrom(NullAddress.TOKEN);
        mintEvent.setTo(assets.getCreator());
        mintEvent.setQuantity(assets.getSupply());
        activity.setMint(mintEvent);

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

    /**
     * Make it can't show expired state
     */
    public void freezeListingEvent(Long activityId) {
        if(null != activityId){
            nftActivityDao.update(
                    NftActivityQuery.newBuilder().id(activityId).build(),
                    NftActivityUpdate.newBuilder().freeze(true).build()
            );
        }
    }

    public void createCancelEvent(NftListing nftListing, String reason) {

        freezeListingEvent(nftListing.getActivityId());

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
