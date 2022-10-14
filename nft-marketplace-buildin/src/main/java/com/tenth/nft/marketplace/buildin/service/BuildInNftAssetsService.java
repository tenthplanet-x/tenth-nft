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
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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

        return detail;
    }

    @Override
    protected String getUnionId(Long id) {
        return UnionIds.wrap(UnionIds.CHANNEL_BUILDIN, id);
    }
}
