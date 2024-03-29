package com.tenth.nft.marketplace.buildin.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.marketplace.buildin.dao.BuildInNftAssetsDao;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftAssets;
import com.tenth.nft.convention.UnionIds;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.dto.NftAssetsDetailDTO;
import com.tenth.nft.marketplace.common.service.AbsNftAssetsService;
import com.tenth.nft.marketplace.common.vo.NftAssetsCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsListRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class BuildInNftAssetsService extends AbsNftAssetsService<BuildInNftAssets> {

    @Autowired
    private RouteClient routeClient;

    private BuildInNftBelongService nftBelongService;

    public BuildInNftAssetsService(
            BuildInNftAssetsDao nftAssetsDao,
            BuildInNftCollectionService nftCollectionService,
            BuildInNftBelongService nftBelongService,
            BuildInNftUbtLogService nftUbtLogService,
            @Lazy BuildInNftListingService nftListingService
    ) {
        super(nftAssetsDao, nftCollectionService, nftBelongService, nftUbtLogService, nftListingService);
        this.nftBelongService = nftBelongService;
    }

    @Override
    protected BuildInNftAssets newNftAssets() {
        return new BuildInNftAssets();
    }

    @Override
    protected void afterCreate(BuildInNftAssets nftAssets) {

    }

    public NftAssetsDTO create(NftAssetsCreateRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        return create(String.valueOf(uid), request);
    }

    public NftAssetsDetailDTO detail(NftAssetsDetailRequest request) {

        String uid = GameUserContext.get().get(TpulseHeaders.UID);

        NftAssetsDetailDTO detail = detail(request, NftAssetsDetailDTO.class);

        NftUserProfileDTO creatorProfileDTO = NftUserProfileDTO.from(
                routeClient.send(
                        Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                .addUids(Long.valueOf(detail.getCreator()))
                                .build(),
                        SearchUserProfileRouteRequest.class
                ).getProfiles(0)
        );
        detail.setCreatorProfile(creatorProfileDTO);

        if(detail.getOwners() == 1){

            Long ownerUid = Long.valueOf(nftBelongService.ownerList(request.getAssetsId()).get(0).getOwner());
            detail.setOwnerProfile(
                    NftUserProfileDTO.from(
                            routeClient.send(
                                    Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                            .addUids(ownerUid)
                                            .build(),
                                    SearchUserProfileRouteRequest.class
                            ).getProfiles(0)
                    )
            );
        }

        detail.setOwns(nftBelongService.owns(request.getAssetsId(), uid));

        return detail;
    }

    @Override
    protected String getUnionId(Long id) {
        return UnionIds.wrap(UnionIds.CHANNEL_BUILDIN, id);
    }

    @Override
    public <DTO extends NftAssetsDTO> Page<DTO> list(NftAssetsListRequest request, Class<DTO> dtoClass) {
        Page<DTO> dataPage = super.list(request, dtoClass);
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
