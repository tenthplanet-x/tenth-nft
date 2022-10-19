package com.tenth.nft.marketplace.buildin.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.UnionIds;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.marketplace.buildin.dao.BuildInNftBelongDao;
import com.tenth.nft.marketplace.buildin.dto.BuildInNftAssetsOwnerDTO;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftBelong;
import com.tenth.nft.marketplace.common.dto.NftAseetsOwnerDTO;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.dto.NftMyAssetsDTO;
import com.tenth.nft.marketplace.common.service.AbsNftBelongService;
import com.tenth.nft.marketplace.common.vo.NftAssetsDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsOwnRequest;
import com.tenth.nft.marketplace.common.vo.NftOwnerListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class BuildInNftBelongService extends AbsNftBelongService<BuildInNftBelong> {

    @Autowired
    private RouteClient routeClient;

    public BuildInNftBelongService(
            BuildInNftBelongDao nftBelongDao,
            @Lazy BuildInNftAssetsService nftAssetsService,
            @Lazy BuildInNftCollectionService nftCollectionService
            ) {
        super(nftBelongDao, nftAssetsService, nftCollectionService);
    }

    @Override
    protected void afterQuantityChange(BuildInNftBelong nftBelong) {

    }

    public Page<BuildInNftAssetsOwnerDTO> ownerList(NftOwnerListRequest request) {
        Page<BuildInNftAssetsOwnerDTO> ownerDataPage = super.ownerList(request, BuildInNftAssetsOwnerDTO.class);

        //fill with userProfile
        Set<Long> fromUids = ownerDataPage.getData().stream().map(dto -> dto.getUid()).filter(Objects::nonNull).map(Long::valueOf).collect(Collectors.toSet());
        Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(fromUids).build(),
                SearchUserProfileRouteRequest.class
        ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
        ownerDataPage.getData().stream().forEach(dto -> {
            dto.setUserProfile(userProfileDTOMap.get(Long.valueOf(dto.getUid())));
        });

        return ownerDataPage;
    }


    public Page<NftMyAssetsDTO> myAssets(NftAssetsOwnRequest request) {

        Page<NftMyAssetsDTO> dataPage = myAssets(request, String.valueOf(request.getOwner()), NftMyAssetsDTO.class);
        if(null != dataPage.getData() && !dataPage.getData().isEmpty()){

            Collection<Long> sellerUids = dataPage.getData().stream().map(dto -> Long.valueOf(dto.getCreator())).collect(Collectors.toSet());
            Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
                    Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(sellerUids).build(),
                    SearchUserProfileRouteRequest.class
            ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
            dataPage.getData().stream().forEach(dto -> {
                dto.setCollectionUnionId(UnionIds.wrap(UnionIds.CHANNEL_BUILDIN, dto.getCollectionId()));
                dto.setCreatorProfile(userProfileDTOMap.get(Long.valueOf(dto.getCreator())));
            });
        }
        return dataPage;
    }
}
