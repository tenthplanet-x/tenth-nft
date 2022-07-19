package com.tenth.nft.exchange.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.routes.exchange.ActivityListRouteRequest;
import com.tenth.nft.convention.routes.exchange.OwnerListRouteRequest;
import com.tenth.nft.exchange.controller.vo.NftOwnerListRequest;
import com.tenth.nft.exchange.dto.NftActivityDTO;
import com.tenth.nft.exchange.dto.NftOwnerDTO;
import com.tenth.nft.orm.marketplace.dao.NftBelongDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftActivityQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftBelongQuery;
import com.tenth.nft.protobuf.NftExchange;
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
public class NftOwnerService {

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftBelongDao nftBelongDao;

    public Page<NftOwnerDTO> list(NftOwnerListRequest request) {
        List<NftOwnerDTO> data = routeClient.send(
                NftExchange.OWNER_LIST_IC.newBuilder()
                        .setAssetsId(request.getAssetsId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .build(),
                OwnerListRouteRequest.class
        ).getOwnersList().stream().map(NftOwnerDTO::from).collect(Collectors.toList());

        //fill with userProfile
        Set<Long> fromUids = data.stream().map(dto -> dto.getUid()).filter(Objects::nonNull).collect(Collectors.toSet());
        Map<Long, UserProfileDTO> userProfileDTOMap = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(fromUids).build(),
                SearchUserProfileRouteRequest.class
        ).getProfilesList().stream().map(NftOwnerService::from).collect(Collectors.toMap(UserProfileDTO::getUid, Function.identity()));
        data.stream().forEach(dto -> {
            dto.setUserProfile(userProfileDTOMap.get(dto.getUid()));
        });

        return new Page<>(0, data);
    }

    public NftExchange.OWNER_LIST_IS list(NftExchange.OWNER_LIST_IC request){

        int page = request.getPage();
        int pageSize = request.getPageSize();

        List<NftExchange.NftOwnerDTO> dtos = nftBelongDao.findPage(
                NftBelongQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .setPage(page)
                        .setPageSize(pageSize)
                        .setSortField("_id")
                        .setReverse(true)
                        .build()
        ).getData().stream().map(NftOwnerDTO::toProto).collect(Collectors.toList());
        return NftExchange.OWNER_LIST_IS.newBuilder()
                .addAllOwners(dtos)
                .build();
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
}
