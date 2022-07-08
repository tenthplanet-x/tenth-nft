package com.tenth.nft.search.service;

import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserFriendProfileRouteRequest;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.CollectionsExchangeProfileRouteRequest;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.NftCollectionDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.search.dto.CollectionDetailSearchDTO;
import com.tenth.nft.search.dto.CollectionSearchDTO;
import com.tenth.nft.search.lucenedao.NftAssetsLuceneDao;
import com.tenth.nft.search.lucenedao.NftCollectionLuceneDao;
import com.tenth.nft.search.vo.CollectionDetailSearchRequest;
import com.tenth.nft.search.vo.CollectionSearchRequest;
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

    public Page<CollectionSearchDTO> list(CollectionSearchRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        List<Long> page = nftCollectionLuceneDao.list(request);
        if(!page.isEmpty()){
            List<CollectionSearchDTO> collections = page.stream().map(id -> {

                CollectionSearchDTO dto = nftCollectionCacheDao.findOne(
                        NftCollectionQuery.newBuilder().id(id).build(),
                        CollectionSearchDTO.class
                );

                String nickname = routeClient.send(
                        Search.SEARCH_USER_FRIEND_PROFILE_IC.newBuilder()
                                .setUid(uid)
                                .addFriendUids(dto.getUid())
                                .build(),
                        SearchUserFriendProfileRouteRequest.class
                ).getProfiles(0).getNickname();
                dto.setCreatorName(nickname);
                dto.setOwned(dto.getUid().equals(uid));

                return dto;

            }).collect(Collectors.toList());
            return new Page<>(
                    0,
                    collections
            );
        }

        return new Page<>();
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
            if(exchangeProfile.hasFloorPrice()){
                collectionSearchDTO.setCurrency(exchangeProfile.getCurrency());
                collectionSearchDTO.setFloorPrice(Prices.toString(exchangeProfile.getFloorPrice()));
            }
            if(exchangeProfile.hasTotalVolume()){
                collectionSearchDTO.setTotalVolume(Prices.toString(exchangeProfile.getTotalVolume()));
            }
        }


        NftUserProfileDTO nftUserProfileDTO = NftUserProfileDTO.from(routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(collectionSearchDTO.getUid()).build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0));
        collectionSearchDTO.setCreatorProfile(nftUserProfileDTO);

        return collectionSearchDTO;

    }




}
