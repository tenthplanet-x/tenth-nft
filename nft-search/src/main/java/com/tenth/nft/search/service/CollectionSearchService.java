package com.tenth.nft.search.service;

import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.CollectionsExchangeProfileRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.marketplace.CollectionDetailRouteRequest;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.search.dto.AssetsSearchDTO;
import com.tenth.nft.search.dto.CollectionDetailSearchDTO;
import com.tenth.nft.search.dto.CollectionSearchDTO;
import com.tenth.nft.search.lucenedao.NftAssetsLuceneDao;
import com.tenth.nft.search.lucenedao.NftCollectionLuceneDao;
import com.tenth.nft.search.vo.CollectionDetailSearchRequest;
import com.tenth.nft.search.vo.CollectionListSearchRequest;
import com.tenth.nft.search.vo.CollectionLuceneSearchParams;
import com.tenth.nft.search.vo.CollectionRecommentListSearchRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class CollectionSearchService {

    @Autowired
    private NftCollectionLuceneDao nftCollectionLuceneDao;
//    @Autowired
//    private NftCollectionDao nftCollectionCacheDao;
//    @Autowired
//    private NftAssetsDao nftItemCacheDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftAssetsLuceneDao nftAssetsLuceneDao;

    public Page<CollectionSearchDTO> list(CollectionListSearchRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        List<Long> page = nftCollectionLuceneDao.list(CollectionLuceneSearchParams.newBuilder()
                .uid(request.getUid())
                .categoryId(request.getCategoryId())
                .page(request.getPage())
                .pageSize(request.getPageSize()).build());

        if(!page.isEmpty()){
            List<CollectionSearchDTO> collections = page.stream().map(id -> {
                return toSearchDTO(uid, id);
            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    collections
            );
        }

        return new Page<>();
    }

    private CollectionSearchDTO toSearchDTO(Long observer, Long collectionId) {
        CollectionSearchDTO dto = CollectionSearchDTO.from(
                routeClient.send(
                        NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                                .setId(collectionId)
                                .build(),
                        CollectionDetailRouteRequest.class
                ).getCollection()
        );

        Search.SearchUserDTO creatorProfileDTO = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(dto.getUid()).build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0);
        dto.setCreatorProfile(NftUserProfileDTO.from(creatorProfileDTO));


        //exchange profile
        List<Long> assetsIds = nftAssetsLuceneDao.listByCollectionId(collectionId);
        if(!assetsIds.isEmpty()){
            NftExchange.NftCollectionProfileDTO exchangeProfile = routeClient.send(
                    NftExchange.COLLECTION_EXCHANGE_PROFILE_IC.newBuilder()
                            .addAllAssetsIds(assetsIds)
                            .setObserver(observer)
                            .build(),
                    CollectionsExchangeProfileRouteRequest.class
            ).getProfile();
            //totalVolume
            if(exchangeProfile.hasTotalVolume()){
                dto.setTotalVolume(Prices.toString(exchangeProfile.getTotalVolume()));
                dto.setCurrency(exchangeProfile.getCurrency());
            }
            dto.setOwned(exchangeProfile.getOwned());
            dto.setOwners(exchangeProfile.getOwners());
        }

        return dto;
    }

    /**
     * 1. Make the current cache of the collection invalidate
     * 2. Reuild lucene indexes of the collection
     * @param collectionId
     */
    public void rebuild(Long collectionId){
        nftCollectionLuceneDao.rebuild(
                routeClient.send(
                        NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                                .setId(collectionId)
                                .build(),
                        CollectionDetailRouteRequest.class
                ).getCollection()
        );
    }

    /**
     * 集合详情
     * @param request
     * @return
     */
    public CollectionDetailSearchDTO detail(CollectionDetailSearchRequest request) {

        CollectionDetailSearchDTO collectionSearchDTO = CollectionSearchDTO.from(
                routeClient.send(
                        NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                                .setId(request.getId())
                                .build(),
                        CollectionDetailRouteRequest.class
                ).getCollection(),
                CollectionDetailSearchDTO.class
        );

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        collectionSearchDTO.setOwned(collectionSearchDTO.getUid().equals(uid));

        List<Long> assetsIds = nftAssetsLuceneDao.listByCollectionId(request.getId());
        if(!assetsIds.isEmpty()){
            NftExchange.NftCollectionProfileDTO exchangeProfile = routeClient.send(
                    NftExchange.COLLECTION_EXCHANGE_PROFILE_IC.newBuilder()
                            .addAllAssetsIds(assetsIds)
                            .build(),
                    CollectionsExchangeProfileRouteRequest.class
            ).getProfile();
            //floorPrice
            //totalVolume
            if(exchangeProfile.hasFloorPrice()){
                collectionSearchDTO.setFloorPrice(Prices.toString(exchangeProfile.getFloorPrice()));
                collectionSearchDTO.setCurrency(exchangeProfile.getCurrency());
            }
            if(exchangeProfile.hasTotalVolume()){
                collectionSearchDTO.setTotalVolume(Prices.toString(exchangeProfile.getTotalVolume()));
                collectionSearchDTO.setCurrency(exchangeProfile.getCurrency());
            }
        }

        NftUserProfileDTO nftUserProfileDTO = NftUserProfileDTO.from(routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(collectionSearchDTO.getUid()).build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0));
        collectionSearchDTO.setCreatorProfile(nftUserProfileDTO);

        return collectionSearchDTO;

    }


    public Page<CollectionSearchDTO> recommendList(CollectionRecommentListSearchRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        List<Long> page = nftCollectionLuceneDao.recommendList(CollectionLuceneSearchParams.newBuilder()
                .page(request.getPage())
                .pageSize(request.getPageSize())
                        .categoryId(request.getCategoryId())
                .build());

        if(!page.isEmpty()){
            List<CollectionSearchDTO> collections = page.stream().map(id -> {
                CollectionSearchDTO collectionDTO = toSearchDTO(uid, id);

                //assets
                List<Long> assetsIds = nftAssetsLuceneDao.listByCollectionId(collectionDTO.getId(), 1, 5);
                if(!assetsIds.isEmpty()){
                    List<AssetsSearchDTO> assets = assetsIds.stream().map(assetsId -> {
                        return AssetsSearchDTO.from(
                                routeClient.send(
                                        NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                                .setId(assetsId)
                                                .build(),
                                        AssetsDetailRouteRequest.class
                                ).getAssets()
                        );
                    }).collect(Collectors.toList());
                    Map<Long, AssetsSearchDTO> assetsMapping = assets.stream().collect(Collectors.toMap(AssetsSearchDTO::getId, Function.identity()));
                    collectionDTO.setRecommendAssets(assetsIds.stream().map(assetsId -> assetsMapping.get(assetsId)).collect(Collectors.toList()));

                    //totalVolume
                    NftExchange.NftCollectionProfileDTO exchangeProfile = routeClient.send(
                            NftExchange.COLLECTION_EXCHANGE_PROFILE_IC.newBuilder()
                                    .addAllAssetsIds(assetsIds)
                                    .build(),
                            CollectionsExchangeProfileRouteRequest.class
                    ).getProfile();
                    if(exchangeProfile.hasTotalVolume()){
                        collectionDTO.setTotalVolume(Prices.toString(exchangeProfile.getTotalVolume()));
                        collectionDTO.setCurrency(exchangeProfile.getCurrency());
                    }
                }
                return collectionDTO;
            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    collections
            );
        }

        return new Page<>();
    }
}
