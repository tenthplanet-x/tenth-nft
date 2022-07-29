package com.tenth.nft.marketplace.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.AssetsExchangeProfileRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.marketplace.CollectionDetailRouteRequest;
import com.tenth.nft.marketplace.dao.PlayerAssetsBelongsDao;
import com.tenth.nft.marketplace.dao.expression.PlayerAssetsBelongsQuery;
import com.tenth.nft.marketplace.dao.expression.PlayerAssetsBelongsUpdate;
import com.tenth.nft.marketplace.dto.AssetsOwnSearchDTO;
import com.tenth.nft.marketplace.entity.PlayerAssetsBelongs;
import com.tenth.nft.marketplace.vo.AssetsOwnSearchRequest;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftPlayer;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class PlayerAssetsBelongsService {

    @Autowired
    private PlayerAssetsBelongsDao playerAssetsBelongsDao;
    @Autowired
    private RouteClient routeClient;

    public void updateAssetsBelongs(NftPlayer.ASSETS_BELONGS_UPDATE_IC request) {

        SimpleQuery query = PlayerAssetsBelongsQuery.newBuilder().uid(request.getUid()).assetsId(request.getAssetsId()).build();

        if(request.getOwns() == 0){
            playerAssetsBelongsDao.remove(query);
        }else{
            playerAssetsBelongsDao.findAndModify(
                    query,
                    PlayerAssetsBelongsUpdate.newBuilder()
                            .setOwns(request.getOwns())
                            .createdAtOnInsert()
                            .build(),
                    UpdateOptions.options().upsert(true)
            );
        }

    }

    public Page<AssetsOwnSearchDTO> list(AssetsOwnSearchRequest request) {

        Page<PlayerAssetsBelongs> page = playerAssetsBelongsDao.findPage(
                PlayerAssetsBelongsQuery.newBuilder().uid(request.getUid()).build()
        );
        if(!page.getData().isEmpty()){
            List<AssetsOwnSearchDTO> assets = page.getData().stream().map(belongs -> {

                Long assetsId = belongs.getAssetsId();

                //detail
                AssetsOwnSearchDTO dto = AssetsOwnSearchDTO.from(
                        routeClient.send(
                                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                        .setId(assetsId)
                                        .build(),
                                AssetsDetailRouteRequest.class
                        ).getAssets()
                );

                //collection Name
                String collectionName = routeClient.send(
                        NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                                .setId(dto.getCollectionId())
                                .build(),
                        CollectionDetailRouteRequest.class
                ).getCollection().getName();
                dto.setCollectionName(collectionName);

                //exchange profile
                NftExchange.NftAssetsProfileDTO exchangeProfile = routeClient.send(
                        NftExchange.ASSETS_EXCHANGE_PROFILE_IC.newBuilder()
                                .setAssetsId(assetsId)
                                .build(),
                        AssetsExchangeProfileRouteRequest.class
                ).getProfile();
                if(exchangeProfile.hasCurrentListing()){
                    dto.setCurrentListing(AssetsOwnSearchDTO.ListingDTO.from(exchangeProfile.getCurrentListing()));
                    dto.getCurrentListing().setSellerProfile(
                            NftUserProfileDTO.from(
                                    routeClient.send(
                                            Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                                    .addUids(dto.getCurrentListing().getSeller())
                                                    .build(),
                                            SearchUserProfileRouteRequest.class
                                    ).getProfiles(0)
                            )
                    );
                }

                return dto;
            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    assets
            );
        }

        return new Page<>();
    }
}
