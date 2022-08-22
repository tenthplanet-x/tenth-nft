package com.tenth.nft.marketplace.service;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.TpulseIdModules;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.convention.routes.exchange.CollectionsExchangeProfileRouteRequest;
import com.tenth.nft.convention.routes.marketplace.CollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.CollectionDetailRouteRequest;
import com.tenth.nft.marketplace.dao.PlayerAssetsDao;
import com.tenth.nft.marketplace.dao.PlayerCollectionDao;
import com.tenth.nft.marketplace.dao.expression.PlayerAssetsQuery;
import com.tenth.nft.marketplace.dao.expression.PlayerCollectionQuery;
import com.tenth.nft.marketplace.dao.expression.PlayerCollectionUpdate;
import com.tenth.nft.marketplace.entity.PlayerAssets;
import com.tenth.nft.marketplace.entity.PlayerCollection;
import com.tenth.nft.marketplace.vo.NftCollectionCreateRequest;
import com.tenth.nft.marketplace.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.vo.NftCollectionListRequest;
import com.tenth.nft.orm.marketplace.dto.NftCollectionDTO;
import com.tenth.nft.orm.marketplace.dto.NftCollectionDetailDTO;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.oss.IGsOssService;
import com.tpulse.gs.oss.qiniu.QiniuProperties;
import com.tpulse.gs.oss.vo.OSSToken;
import com.tpulse.gs.oss.vo.OSSTokenCreateOption;
import com.tpulse.gs.oss.vo.OSSTokenType;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 10:06
 */
@Service
public class PlayerCollectionService {

    @Autowired
    private IGsOssService gsOssService;
    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private PlayerAssetsDao playerAssetsDao;
    @Autowired
    private PlayerCollectionDao playerCollectionDao;

    public OSSToken getUploadToken() {
        return gsOssService.token(OSSTokenCreateOption.newBuilder()
                .bucket(qiniuProperties.getDefaultBucket())
                .keyPrefix("tmp/")
                .type(OSSTokenType.FILE)
                .build()
        );
    }

    public NftCollectionDTO create(NftCollectionCreateRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Long id = gsCollectionIdService.incrementAndGet(TpulseIdModules.COLLECTION);
        String logoImageDir = request.getLogoImage().substring(request.getLogoImage().indexOf("tmp/") + 4, request.getLogoImage().lastIndexOf("/"));
        String logoImageUrl = gsOssService.changeDir(request.getLogoImage(), logoImageDir);
        String featuredImageDir = request.getFeaturedImage().substring(request.getFeaturedImage().indexOf("tmp/") + 4, request.getFeaturedImage().lastIndexOf("/"));
        String featuredImageUrl = gsOssService.changeDir(request.getFeaturedImage(), featuredImageDir);
        NftCollection nftCollection = new NftCollection();
        nftCollection.setId(id);
        nftCollection.setUid(uid);
        nftCollection.setCreatedAt(System.currentTimeMillis());
        nftCollection.setUpdatedAt(System.currentTimeMillis());
        nftCollection.setName(request.getName());
        nftCollection.setDesc(request.getDesc());
        nftCollection.setLogoImage(logoImageUrl);
        nftCollection.setFeaturedImage(featuredImageUrl);
        nftCollection.setCategory(request.getCategory());
        nftCollection.setCreatorFeeRate(request.getCreatorFeeRate());
        nftCollection.setBlockchain(request.getBlockchain());
        NftMarketplace.CollectionDTO collectionDTO = routeClient.send(
                NftMarketplace.COLLECTION_CREATE_IC.newBuilder()
                        .setCollection(NftCollectionDTO.toProto(nftCollection))
                        .build(),
                CollectionCreateRouteRequest.class
        ).getCollection();

        //insert relation
        PlayerCollection playerCollection = new PlayerCollection();
        playerCollection.setCollectionId(id);
        playerCollection.setUid(uid);
        playerCollection.setCreatedAt(System.currentTimeMillis());
        playerCollection.setUpdatedAt(playerCollection.getCreatedAt());
        playerCollection.setItems(0);
        playerCollection.setCreatorFeeRate(request.getCreatorFeeRate());
        playerCollectionDao.insert(playerCollection);

        NftCollectionDTO nftCollectionDTO = NftCollectionDTO.from(collectionDTO);
//        nftCollectionDTO.setCreatorProfile(
//                NftUserProfileDTO.from(
//                        routeClient.send(
//                                Search.SEARCH_USER_PROFILE_IC.newBuilder()
//                                        .addUids(uid)
//                                        .build(),
//                                SearchUserProfileRouteRequest.class
//                        ).getProfiles(0)
//                )
//        );
        return nftCollectionDTO;
    }

    public Page<NftCollectionDTO> list(NftCollectionListRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<PlayerCollection> dataPage = playerCollectionDao.findPage(
                PlayerCollectionQuery.newBuilder()
                        .uid(uid)
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build()
        );

        //profile
        Search.SearchUserDTO creatorProfile = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder()
                        .addUids(uid)
                        .build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0);

        return new Page<>(
                0,
                dataPage.getData().stream().map(entity -> {
                    //detail
                    NftCollectionDTO dto = NftCollectionDTO.from(
                            routeClient.send(
                                    NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                                            .setId(entity.getCollectionId())
                                            .build(),
                                    CollectionDetailRouteRequest.class
                            ).getCollection()
                    );
                    dto.setCreatorProfile(NftUserProfileDTO.from(creatorProfile));
                    //exchange profile
                    //TODO
                    return dto;
                }).collect(Collectors.toList())
        );
    }

    public NftCollectionDTO detail(NftCollectionDetailRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        //detail
        NftCollectionDetailDTO dto = NftCollectionDetailDTO.from(
                routeClient.send(
                        NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                                .setId(request.getId())
                                .build(),
                        CollectionDetailRouteRequest.class
                ).getCollection(),
                NftCollectionDetailDTO.class
        );

        //user profile
        Search.SearchUserDTO createProfile = routeClient.send(
                Search.SEARCH_USER_PROFILE_IC.newBuilder()
                        .addUids(dto.getUid())
                        .build(),
                SearchUserProfileRouteRequest.class
        ).getProfiles(0);
        dto.setCreatorProfile(NftUserProfileDTO.from(createProfile));

        //totalVolume
        //owners
        List<Long> assetsIds = playerAssetsDao.find(PlayerAssetsQuery.newBuilder().uid(uid).setCollectionId(request.getId()).build()).stream().map(PlayerAssets::getAssetsId).collect(Collectors.toList());
        if(!assetsIds.isEmpty()){
            NftExchange.NftCollectionProfileDTO exchangeProfile = routeClient.send(
                    NftExchange.COLLECTION_EXCHANGE_PROFILE_IC.newBuilder()
                            .addAllAssetsIds(assetsIds)
                            .setObserver(uid)
                            .build(),
                    CollectionsExchangeProfileRouteRequest.class
            ).getProfile();

            if(exchangeProfile.hasFloorPrice()){
                dto.setFloorPrice(exchangeProfile.getFloorPrice());
                dto.setCurrency(exchangeProfile.getCurrency());
            }
            if(exchangeProfile.hasTotalVolume()){
                dto.setTotalVolume(exchangeProfile.getTotalVolume());
                dto.setCurrency(exchangeProfile.getCurrency());
            }
            dto.setOwners(exchangeProfile.getOwners());
            dto.setOwned(exchangeProfile.getOwned());
        }else{
            dto.setOwners(1);
            dto.setOwned(true);
        }

        return dto;
    }

    public void updateItems(Long uid, Long collectionId, Integer items) {
        playerCollectionDao.update(
                PlayerCollectionQuery.newBuilder().uid(uid).collectionId(collectionId).build(),
                PlayerCollectionUpdate.newBuilder().setItems(items).build()
        );
        //rebuildCache(collectionId);
        routeClient.send(
                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
                        .setCollectionId(collectionId)
                        .build(),
                CollectionRebuildRouteRequest.class
        );
    }

    public PlayerCollection detail(Long uid, Long collectionId) {
        return playerCollectionDao.findOne(PlayerCollectionQuery.newBuilder().uid(uid).collectionId(collectionId).build());
    }

}
