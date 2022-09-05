package com.tenth.nft.exchange.common.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.routes.exchange.OwnerListRouteRequest;
import com.tenth.nft.convention.routes.player.AssetsBelongsUpdateRouteRequest;
import com.tenth.nft.exchange.buildin.controller.vo.NftOwnerListRequest;
import com.tenth.nft.exchange.buildin.dto.NftOwnerDTO;
import com.tenth.nft.orm.marketplace.dao.NftBelongDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftBelongQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftBelongUpdate;
import com.tenth.nft.orm.marketplace.entity.NftBelong;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftPlayer;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
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
public class NftBelongService {

    @Autowired
    private NftBelongDao nftBelongDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftActivityService nftActivityService;

    public NftBelong findOne(Long assetsId, Long owner) {
        SimpleQuery preBelongQuery = NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build();
        return nftBelongDao.findOne(preBelongQuery);
    }

    public void create(long assetsId, long owner, int quantity) {

        NftBelong belong = new NftBelong();
        belong.setOwner(owner);
        belong.setAssetsId(assetsId);
        belong.setQuantity(quantity);
        belong.setCreatedAt(System.currentTimeMillis());
        belong.setUpdatedAt(belong.getCreatedAt());
        nftBelongDao.insert(belong);
        //Sync to player
        syncOwnsToPlayer(belong);
    }

    public void dec(Long assetsId, Long owner, int quantity) {

        NftBelong belong = nftBelongDao.findAndModify(
                NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build(),
                NftBelongUpdate.newBuilder().quantityInc(-quantity).build(),
                UpdateOptions.options().returnNew(true)
        );
        if(belong.getQuantity() <= 0){
            nftBelongDao.remove(NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build());
        }
        //Sync to player
        syncOwnsToPlayer(belong);
    }

    private void syncOwnsToPlayer(NftBelong belong) {
        routeClient.send(
                NftPlayer.ASSETS_BELONGS_UPDATE_IC.newBuilder()
                        .setUid(belong.getOwner())
                        .setAssetsId(belong.getAssetsId())
                        .setOwns(belong.getQuantity())
                        .build(),
                AssetsBelongsUpdateRouteRequest.class
        );
    }

    public long ownersOf(Long assetsId) {
        return nftBelongDao.count(NftBelongQuery.newBuilder().assetsId(assetsId).build());
    }

    public List<NftBelong> findAll(Long assetsId) {
        return nftBelongDao.find(NftBelongQuery.newBuilder().assetsId(assetsId).build());
    }

    public NftBelong inc(Long assetsId, Long owner, Integer quantity) {

        NftBelong nftBelong = nftBelongDao.findAndModify(
                NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build(),
                NftBelongUpdate.newBuilder()
                        .quantityInc(quantity)
                        .createdAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true).returnNew(true)
        );
        //Send new quantity of the assets to player
        syncOwnsToPlayer(nftBelong);

        return nftBelong;
    }

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
        ).getProfilesList().stream().map(NftBelongService::from).collect(Collectors.toMap(UserProfileDTO::getUid, Function.identity()));
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
