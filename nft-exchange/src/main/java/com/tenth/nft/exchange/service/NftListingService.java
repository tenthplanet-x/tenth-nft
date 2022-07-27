package com.tenth.nft.exchange.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.routes.exchange.ListingListRouteRequest;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.exchange.controller.vo.NftListingListRequest;
import com.tenth.nft.exchange.dto.NftListingDTO;
import com.tenth.nft.orm.marketplace.dao.NftListingDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftListingQuery;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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

}
