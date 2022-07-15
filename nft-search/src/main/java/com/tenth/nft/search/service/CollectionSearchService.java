package com.tenth.nft.search.service;

import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserFriendProfileRouteRequest;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.CollectionsExchangeProfileRouteRequest;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.NftCollectionDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.search.dto.AssetsDetailSearchDTO;
import com.tenth.nft.search.dto.AssetsSearchDTO;
import com.tenth.nft.search.dto.CollectionDetailSearchDTO;
import com.tenth.nft.search.dto.CollectionSearchDTO;
import com.tenth.nft.search.lucenedao.NftAssetsLuceneDao;
import com.tenth.nft.search.lucenedao.NftCollectionLuceneDao;
import com.tenth.nft.search.vo.CollectionDetailSearchRequest;
import com.tenth.nft.search.vo.CollectionListSearchRequest;
import com.tenth.nft.search.vo.CollectionLuceneSearchParams;
import com.tpulse.commons.biz.dto.PageRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    @Autowired
    private NftCollectionDao nftCollectionCacheDao;
    @Autowired
    private NftAssetsDao nftItemCacheDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftAssetsLuceneDao nftAssetsLuceneDao;

    public Page<CollectionSearchDTO> list(CollectionListSearchRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        List<Long> page = nftCollectionLuceneDao.list(CollectionLuceneSearchParams.newBuilder()
                .uid(uid)
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

    private CollectionSearchDTO toSearchDTO(Long uid, Long collectionId) {
        CollectionSearchDTO dto = nftCollectionCacheDao.findOne(
                NftCollectionQuery.newBuilder().id(collectionId).build(),
                CollectionSearchDTO.class
        );

        Search.SearchUserDTO creatorProfileDTO = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(dto.getUid()).build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0);
        dto.setCreatorProfile(NftUserProfileDTO.from(creatorProfileDTO));
        dto.setCreatorName(dto.getCreatorProfile().getNickname());
        dto.setOwned(dto.getUid().equals(uid));

        return dto;
    }

    /**
     * 1. Make the current cache of the collection invalidate
     * 2. Reuild lucene indexes of the collection
     * @param collectionId
     */
    public void rebuild(Long collectionId){

        nftCollectionCacheDao.clearCache(collectionId);

        NftCollection nftCollection = nftCollectionCacheDao.findOne(NftCollectionQuery.newBuilder().id(collectionId).build());
        nftCollectionLuceneDao.rebuild(nftCollection);
    }

    /**
     * 集合详情
     * @param request
     * @return
     */
    public CollectionDetailSearchDTO detail(CollectionDetailSearchRequest request) {

        CollectionDetailSearchDTO collectionSearchDTO = nftCollectionCacheDao.findOne(
                NftCollectionQuery.newBuilder().id(request.getId()).build(),
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
            if(exchangeProfile.hasCurrentListing()){
                AssetsDetailSearchDTO.ListingDTO floorListing = AssetsDetailSearchDTO.ListingDTO.from(exchangeProfile.getCurrentListing());
                collectionSearchDTO.setFloorPrice(floorListing.getPrice());
                collectionSearchDTO.setCurrency(floorListing.getCurrency());
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


    public Page<CollectionSearchDTO> recommendList(PageRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        List<Long> page = nftCollectionLuceneDao.recommendList(CollectionLuceneSearchParams.newBuilder()
                .page(request.getPage())
                .pageSize(request.getPageSize())
                .build());

        if(!page.isEmpty()){
            List<CollectionSearchDTO> collections = page.stream().map(id -> {
                CollectionSearchDTO collectionDTO = toSearchDTO(uid, id);

                //assets
                List<Long> assetsIds = nftAssetsLuceneDao.listByCollectionId(collectionDTO.getId(), 1, 5);
                if(!assetsIds.isEmpty()){
                    List<AssetsSearchDTO> assets = assetsIds.stream().map(assetsId -> {
                        return nftItemCacheDao.findOne(NftAssetsQuery.newBuilder().id(assetsId).build(), AssetsSearchDTO.class);
                    }).collect(Collectors.toList());
                    Map<Long, AssetsSearchDTO> assetsMapping = assets.stream().collect(Collectors.toMap(AssetsSearchDTO::getId, Function.identity()));
                    collectionDTO.setRecommendAssets(assetsIds.stream().map(assetsId -> assetsMapping.get(assetsId)).collect(Collectors.toList()));
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
