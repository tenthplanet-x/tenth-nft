package com.tenth.nft.marketplace.buildin.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.marketplace.stats.CollectionVolumeStatsRouteRequest;
import com.tenth.nft.marketplace.buildin.dao.BuildInNftCollectionDao;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftCollection;
import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.common.dto.NftCollectionDetailDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.service.AbsNftCollectionService;
import com.tenth.nft.marketplace.common.vo.NftCollectionCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionListRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionOwnListRequest;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class BuildInNftCollectionService extends AbsNftCollectionService<BuildInNftCollection> {

    @Autowired
    private RouteClient routeClient;

    private BuildInNftListingService buildInNftListingService;

    public BuildInNftCollectionService(
            BuildInNftCollectionDao nftCollectionDao,
            @Lazy BuildInNftAssetsService buildInNftAssetsService,
            @Lazy BuildInNftListingService buildInNftListingService
            ) {
        super(nftCollectionDao, buildInNftAssetsService);
        this.buildInNftListingService = buildInNftListingService;
    }

    public NftCollectionDTO create(NftCollectionCreateRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String creator = String.valueOf(uid);
        return create(creator, request, NftCollectionDTO.class);
    }

    @Override
    protected BuildInNftCollection newCollection() {
        return new BuildInNftCollection();
    }

    @Override
    protected void afterInsert(AbsNftCollection collection) {
        rebuild(collection.getId());
    }

    private void rebuild(Long collectionId) {
//        routeClient.send(
//                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
//                        .setCollectionId(collectionId)
//                        .build(),
//                CollectionRebuildRouteRequest.class
//        );
    }

    public Page<NftCollectionDTO> list(NftCollectionListRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Page<NftCollectionDTO> dataPage = super.list(request, Optional.of(String.valueOf(uid)), NftCollectionDTO.class);

        NftUserProfileDTO userProfileDTO = NftUserProfileDTO.from(
                routeClient.send(
                        Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                .addUids(uid)
                                .build(),
                        SearchUserProfileRouteRequest.class
                ).getProfiles(0)
        );

        return new Page<>(
                0,
                dataPage.getData().stream().map(dto -> {
                    dto.setCreatorProfile(userProfileDTO);
                    return dto;
                }).collect(Collectors.toList())
        );
    }

    public NftCollectionDetailDTO detail(NftCollectionDetailRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftUserProfileDTO creatorProfileDTO = NftUserProfileDTO.from(
                routeClient.send(
                        Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                .addUids(uid)
                                .build(),
                        SearchUserProfileRouteRequest.class
                ).getProfiles(0)
        );

        NftCollectionDetailDTO dto = detail(request, NftCollectionDetailDTO.class);
        dto.setCreatorProfile(creatorProfileDTO);

        //floor price
        Optional<BigDecimal> floorPrice = buildInNftListingService.getFloorPrice(request.getId());
        if(floorPrice.isPresent()){
            dto.setFloorPrice(floorPrice.get().toPlainString());
        }

        //totalVolume
        NftMarketplaceStats.COLLECTION_VOLUME_STATS_IS routeResponse = routeClient.send(
                NftMarketplaceStats.COLLECTION_VOLUME_STATS_IC.newBuilder()
                        .setCollectionId(request.getId())
                        .build(),
                CollectionVolumeStatsRouteRequest.class
        );
        if(routeResponse.hasStats()){
            dto.setTotalVolume(routeResponse.getStats().getTotalVolume());
            dto.setCurrency(routeResponse.getStats().getCurrency());
        }

        return dto;
    }


    public Page<NftCollectionDTO> ownList(NftCollectionOwnListRequest request) {
        return list(request, Optional.of(String.valueOf(request.getOwner())), NftCollectionDTO.class);
    }
}
