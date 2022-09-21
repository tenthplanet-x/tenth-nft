package com.tenth.nft.search.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.AssetsExchangeProfileRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AbsAssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionDetailRouteRequest;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.search.dto.AssetsOwnSearchDTO;
import com.tenth.nft.search.dto.AssetsSearchDTO;
import com.tenth.nft.search.lucenedao.NftAssetsLuceneDao;
import com.tenth.nft.search.dto.AssetsDetailSearchDTO;
import com.tenth.nft.search.vo.AssetsDetailSearchRequest;
import com.tenth.nft.search.vo.AssetsOwnSearchRequest;
import com.tenth.nft.search.vo.AssetsSearchRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class AssetsSearchService {

    @Autowired
    private NftAssetsLuceneDao nftAssetsLuceneDao;
    @Autowired
    private RouteClient routeClient;

    public Page<AssetsSearchDTO> list(AssetsSearchRequest request) {

        List<Long> page = nftAssetsLuceneDao.list(request);
        if(!page.isEmpty()){
            List<AssetsSearchDTO> assets = page.stream().map(id -> {

                //detail
                AssetsSearchDTO assetsSearchDTO = AssetsSearchDTO.from(
                        routeClient.send(
                                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                        .setId(id)
                                        .build(),
                                AbsAssetsDetailRouteRequest.class
                        ).getAssets()
                );

                //exchange profile
                NftExchange.NftAssetsProfileDTO exchangeProfile = routeClient.send(
                        NftExchange.ASSETS_EXCHANGE_PROFILE_IC.newBuilder()
                                .setAssetsId(id)
                                .build(),
                        AssetsExchangeProfileRouteRequest.class
                ).getProfile();
                if(exchangeProfile.hasCurrentListing()){
                    assetsSearchDTO.setCurrentListing(AssetsDetailSearchDTO.ListingDTO.from(exchangeProfile.getCurrentListing()));
                    assetsSearchDTO.getCurrentListing().setSellerProfile(
                            NftUserProfileDTO.from(
                                    routeClient.send(
                                            Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                                    .addUids(assetsSearchDTO.getCurrentListing().getSeller())
                                                    .build(),
                                            SearchUserProfileRouteRequest.class
                                    ).getProfiles(0)
                            )
                    );
                }

                return assetsSearchDTO;

            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    assets
            );
        }

        return new Page<>();
    }

    public void rebuild(Long assetsId){
        nftAssetsLuceneDao.rebuild(
                routeClient.send(
                        NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                .setId(assetsId)
                                .build(),
                        AbsAssetsDetailRouteRequest.class
                ).getAssets()
        );
    }

    public AssetsDetailSearchDTO detail(AssetsDetailSearchRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        //detail
        AssetsDetailSearchDTO dto = AssetsSearchDTO.from(
                routeClient.send(
                        NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                .setId(request.getAssetsId())
                                .build(),
                        AbsAssetsDetailRouteRequest.class
                ).getAssets(),
                AssetsDetailSearchDTO.class
        );

        Search.SearchUserDTO searchUserDTO = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder()
                        .addUids(dto.getCreator())
                        .build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0);
        UserProfileDTO userProfileDTO = NftUserProfileDTO.from(searchUserDTO);
        dto.setCreatorProfile(userProfileDTO);

        //exchange profile
        NftExchange.NftAssetsProfileDTO exchangeProfile = routeClient.send(
                NftExchange.ASSETS_EXCHANGE_PROFILE_IC.newBuilder()
                        .setAssetsId(request.getAssetsId())
                        .setObserver(uid)
                        .build(),
                AssetsExchangeProfileRouteRequest.class
        ).getProfile();
        if(exchangeProfile.hasCurrentListing()){
            AssetsDetailSearchDTO.ListingDTO listingDTO = AssetsDetailSearchDTO.ListingDTO.from(exchangeProfile.getCurrentListing());
            listingDTO.setSellerProfile(
                    NftUserProfileDTO.from(
                            routeClient.send(
                                    Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                            .addUids(listingDTO.getSeller())
                                            .build(),
                                    SearchUserProfileRouteRequest.class
                            ).getProfiles(0)
                    )
            );
            dto.setCurrentListing(listingDTO);
        }
        if(exchangeProfile.hasTotalVolume()){
            dto.setTotalVolume(exchangeProfile.getTotalVolume());
            dto.setCurrency(exchangeProfile.getCurrency());
        }
        dto.setOwners(exchangeProfile.getOwners());
        dto.setOwns(exchangeProfile.getOwns());
        if(dto.getSupply() == 1 || dto.getOwners() == 1){
            Search.SearchUserDTO ownerUserDTO = routeClient.send(
                    Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(exchangeProfile.getOwnerListsList()).build(),
                    SearchUserProfileRouteRequest.class
            ).getProfiles(0);
            dto.setOwnerProfile(NftUserProfileDTO.from(ownerUserDTO));
        }
        //current offer
        if(exchangeProfile.hasBestOffer()){
            dto.setBestOffer(AssetsDetailSearchDTO.NftOfferDTO.from(exchangeProfile.getBestOffer()));
        }

        return dto;
    }

    public Page<AssetsOwnSearchDTO> list(AssetsOwnSearchRequest request) {

        List<Long> page = nftAssetsLuceneDao.list(request);
        if(!page.isEmpty()){
            List<AssetsOwnSearchDTO> assets = page.stream().map(id -> {

                //detail
                AssetsOwnSearchDTO dto = AssetsOwnSearchDTO.from(
                        routeClient.send(
                                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                        .setId(id)
                                        .build(),
                                AbsAssetsDetailRouteRequest.class
                        ).getAssets()
                );

                //collection Name
                String collectionName = routeClient.send(
                        NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                                .setId(dto.getCollectionId())
                                .build(),
                        AbsCollectionDetailRouteRequest.class
                ).getCollection().getName();
                dto.setCollectionName(collectionName);

                //exchange profile
                NftExchange.NftAssetsProfileDTO exchangeProfile = routeClient.send(
                        NftExchange.ASSETS_EXCHANGE_PROFILE_IC.newBuilder()
                                .setAssetsId(id)
                                .build(),
                        AssetsExchangeProfileRouteRequest.class
                ).getProfile();
                if(exchangeProfile.hasCurrentListing()){
                    dto.setCurrentListing(AssetsDetailSearchDTO.ListingDTO.from(exchangeProfile.getCurrentListing()));
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
