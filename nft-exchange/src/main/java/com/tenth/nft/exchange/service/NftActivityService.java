package com.tenth.nft.exchange.service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.qcloud.cos.transfer.Transfer;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.ActivityListRouteRequest;
import com.tenth.nft.convention.routes.exchange.ListingListRouteRequest;
import com.tenth.nft.exchange.controller.vo.NftActivityListRequest;
import com.tenth.nft.exchange.dto.NftActivityDTO;
import com.tenth.nft.exchange.dto.NftListingDTO;
import com.tenth.nft.orm.marketplace.dao.NftActivityDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftActivityQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftListingQuery;
import com.tenth.nft.orm.marketplace.entity.NftActivity;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.orm.marketplace.entity.event.ListEvent;
import com.tenth.nft.orm.marketplace.entity.event.SaleEvent;
import com.tenth.nft.orm.marketplace.entity.event.TransferEvent;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

        List<NftActivityDTO> data = routeClient.send(
                NftExchange.ACTIVITY_LIST_IC.newBuilder()
                        .setAssetsId(request.getAssetsId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .build(),
                ActivityListRouteRequest.class
        ).getActivitiesList().stream().map(NftActivityDTO::from).collect(Collectors.toList());

        //fill with userProfile
        Set<Long> fromUids = data.stream().map(dto -> dto.getFrom()).filter(Objects::nonNull).collect(Collectors.toSet());
        Set<Long> toUids = data.stream().map(dto -> dto.getTo()).filter(Objects::nonNull).collect(Collectors.toSet());
        fromUids.addAll(toUids);
        Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(fromUids).build(),
                SearchUserProfileRouteRequest.class
        ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
        data.stream().forEach(dto -> {
            if(null != dto.getFrom()){
                dto.setFromProfile(userProfileDTOMap.get(dto.getFrom()));
            }
            if(null != dto.getTo()){
                dto.setToProfile(userProfileDTOMap.get(dto.getTo()));
            }
        });

        return new Page<>(0, data);
    }

    public NftExchange.ACTIVITY_LIST_IS list(NftExchange.ACTIVITY_LIST_IC request){

        int page = request.getPage();
        int pageSize = request.getPageSize();

        List<NftExchange.NftActivityDTO> dtos = nftActivityDao.findPage(
                NftActivityQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .setPage(page)
                        .setPageSize(pageSize)
                        .setSortField("_id")
                        .setReverse(true)
                        .build()
        ).getData().stream().map(NftActivityDTO::toProto).collect(Collectors.toList());
        return NftExchange.ACTIVITY_LIST_IS.newBuilder()
                .addAllActivities(dtos)
                .build();
    }




}
