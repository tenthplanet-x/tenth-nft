package com.tenth.nft.marketplace.buildin.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.marketplace.buildin.dto.BuildInNftCollectionDTO;
import com.tenth.nft.marketplace.buildin.entity.BuildInNftCollection;
import com.tenth.nft.marketplace.common.dao.AbsNftCollectionDao;
import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.service.AbsNftCollectionService;
import com.tenth.nft.marketplace.common.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionListRequest;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class BuildInNftCollectionService extends AbsNftCollectionService<BuildInNftCollection> {

    @Autowired
    private RouteClient routeClient;

    public BuildInNftCollectionService(AbsNftCollectionDao<BuildInNftCollection> nftCollectionDao) {
        super(nftCollectionDao);
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

    public Page<BuildInNftCollectionDTO> list(NftCollectionListRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Page<BuildInNftCollectionDTO> dataPage = super.list(request, Optional.of(String.valueOf(uid)), BuildInNftCollectionDTO.class);

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

    public BuildInNftCollectionDTO detail(NftCollectionDetailRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftUserProfileDTO creatorProfileDTO = NftUserProfileDTO.from(
                routeClient.send(
                        Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                .addUids(uid)
                                .build(),
                        SearchUserProfileRouteRequest.class
                ).getProfiles(0)
        );

        BuildInNftCollectionDTO dto = detail(request, BuildInNftCollectionDTO.class);
        dto.setCreatorProfile(creatorProfileDTO);

        return dto;
    }
}
