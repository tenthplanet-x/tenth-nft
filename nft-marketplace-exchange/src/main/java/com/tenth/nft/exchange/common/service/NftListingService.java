package com.tenth.nft.exchange.common.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.routes.AssetsRebuildRouteRequest;
import com.tenth.nft.convention.routes.exchange.ListingEventRouteRequest;
import com.tenth.nft.convention.routes.exchange.ListingListRouteRequest;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.exchange.buildin.controller.vo.NftListingListRequest;
import com.tenth.nft.exchange.buildin.dto.NftListingDTO;
import com.tenth.nft.orm.marketplace.dao.NftListingDao;
import com.tenth.nft.orm.marketplace.dao.expression.*;
import com.tenth.nft.orm.marketplace.entity.NftBelong;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.orm.marketplace.entity.event.ListCancelEventReason;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class NftListingService {

    @Autowired
    private NftListingDao nftListingDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftBelongService nftBelongService;
    @Autowired
    private NftActivityService nftExchangeEventService;

    public Page<NftListingDTO> list(NftListingListRequest request) {

        List<NftListingDTO> data = routeClient.send(
                NftExchange.LISTING_LIST_IC.newBuilder()
                        .setAssetsId(request.getAssetsId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .build(),
                ListingListRouteRequest.class
        ).getListingsList().stream().map(NftListingDTO::from).collect(Collectors.toList());

        //fill with userProfile
        Collection<Long> sellerUids = data.stream().map(dto -> dto.getSeller()).collect(Collectors.toSet());
        Map<Long, UserProfileDTO> userProfileDTOMap = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(sellerUids).build(),
                SearchUserProfileRouteRequest.class
        ).getProfilesList().stream().map(NftListingService::from).collect(Collectors.toMap(UserProfileDTO::getUid, Function.identity()));
        data.stream().forEach(dto -> {
            dto.setSellerProfile(userProfileDTOMap.get(dto.getSeller()));
        });

        return new Page<>(0, data);
    }

    private static UserProfileDTO from(Search.SearchUserDTO searchUserDTO) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setNickname(searchUserDTO.getNickname());
        userProfileDTO.setUid(searchUserDTO.getUid());
        userProfileDTO.setFaceImg(searchUserDTO.getFaceImg());
        userProfileDTO.setGender(searchUserDTO.getGender());
        userProfileDTO.setAge(searchUserDTO.getAge());
        return userProfileDTO;
    }

    public NftExchange.LISTING_LIST_IS list(NftExchange.LISTING_LIST_IC request){

        int page = request.getPage();
        int pageSize = request.getPageSize();

        List<NftExchange.NftListingDTO> dtos = nftListingDao.findPage(
                NftListingQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .setPage(page)
                        .setPageSize(pageSize)
                        .setSortField("_id")
                        .setReverse(true)
                        .build()
        ).getData().stream().filter(dto -> !Times.isExpired(dto.getExpireAt())).map(NftListingDTO::toProto).collect(Collectors.toList());
        return NftExchange.LISTING_LIST_IS.newBuilder()
                .addAllListings(dtos)
                .build();
    }

    public NftListing insert(NftListing listing) {

        //Insert listing event
        Long activityId = nftExchangeEventService.sendListingEvent(listing);
        listing.setActivityId(activityId);
        listing = nftListingDao.insert(listing);
        //Rebuild cache
        sendListingRouteEventToStats(listing.getAssetsId());
        sendListingRouteEventToSearch(listing.getAssetsId());
        return listing;
    }

    public NftListing findOne(Long assetsId, Long listingId) {
        return nftListingDao.findOne(
                NftListingQuery.newBuilder().assetsId(assetsId).id(listingId).build()
        );
    }

    /**
     * It doesn't send cancel event
     * @param assetsId
     * @param listingId
     */
    public void remove(Long assetsId, Long listingId) {
        NftListing nftListing = nftListingDao.findAndRemove(
                NftListingQuery.newBuilder().assetsId(assetsId).id(listingId).build()
        );
        nftExchangeEventService.freezeListingEvent(nftListing.getActivityId());
        sendListingRouteEventToStats(assetsId);
        sendListingRouteEventToSearch(assetsId);
    }

    public void cancel(Long assetsId, Long listingId, String reason) {
        NftListing nftListing = nftListingDao.findAndRemove(
                NftListingQuery.newBuilder().assetsId(assetsId).id(listingId).build()
        );
        if(null == nftListing){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_CANCEL_EXCEPTION_NOT_EXIST);
        }
        nftExchangeEventService.createCancelEvent(nftListing, reason);
        sendListingRouteEventToStats(assetsId);
        sendListingRouteEventToSearch(nftListing.getAssetsId());
    }

    public void cancelBatch(Long assetsId, List<Long> listingIds, String reason){
        for(Long listingId: listingIds){
            NftListing nftListing = nftListingDao.findAndRemove(
                    NftListingQuery.newBuilder().assetsId(assetsId).id(listingId).build()
            );
            nftExchangeEventService.freezeListingEvent(nftListing.getActivityId());
            nftExchangeEventService.createCancelEvent(nftListing, reason);
        }
        sendListingRouteEventToStats(assetsId);
        sendListingRouteEventToSearch(assetsId);
    }

    /**
     * 1. Remove listings quantity lower than current owns of assets
     * @param owner
     * @param assetsId
     */
    public void refreshListingsBelongTo(Long owner, Long assetsId) {
        int rest = 0;
        NftBelong preBelong = nftBelongService.findOne(assetsId, owner);
        if(preBelong != null){
            rest = preBelong.getQuantity();
        }
        //
        final int _rest = rest;
        List<Long> events = nftListingDao
            .find(NftListingQuery.newBuilder().assetsId(assetsId).uid(owner).canceled(false).build())
            .stream().filter(entity -> entity.getQuantity() > _rest).map(entity -> entity.getId()).filter(Objects::nonNull).collect(Collectors.toList());
        if(!events.isEmpty()){
            cancelBatch(assetsId, events, ListCancelEventReason.QUANTITY.name());
        }

    }

    public Optional<NftListing> findFloorListing(Long assetsId, String specificCurrency) {
        Optional<NftListing> floor = nftListingDao
                .find(NftListingQuery.newBuilder().assetsId(assetsId).build())
                .stream()
                .filter(dto -> !Times.isExpired(dto.getExpireAt()))
                .filter(dto -> dto.getCurrency().equals(specificCurrency))
                .sorted(Comparator.comparingLong(NftListing::getCreatedAt))
                .min(Comparator.comparing(listing -> new BigDecimal(listing.getPrice())));
        return floor;
    }

    private void sendListingRouteEventToStats(Long assetsId) {
        routeClient.send(
                NftExchange.LISTING_EVENT_IC.newBuilder().setAssetsId(assetsId).build(),
                ListingEventRouteRequest.class
        );
    }


    private void sendListingRouteEventToSearch(Long assetsId) {

        //rebuild assets
        routeClient.send(
                NftSearch.NFT_ASSETS_REBUILD_IC.newBuilder()
                        .setAssetsId(assetsId)
                        .build(),
                AssetsRebuildRouteRequest.class
        );
    }


}
