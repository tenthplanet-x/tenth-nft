package com.tenth.nft.marketplace.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.NftModules;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.routes.exchange.AssetsExchangeProfileRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.marketplace.dao.PlayerAssetsDao;
import com.tenth.nft.marketplace.dao.expression.PlayerAssetsQuery;
import com.tenth.nft.orm.marketplace.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.entity.PlayerAssets;
import com.tenth.nft.marketplace.entity.PlayerCollection;
import com.tenth.nft.marketplace.vo.*;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.oss.IGsOssService;
import com.tpulse.gs.oss.qiniu.QiniuProperties;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 10:06
 */
@Service
public class PlayerAssetsService {

//    @Autowired
//    private NftAssetsNoCacheDao nftAssetsDao;
    @Autowired
    private IGsOssService gsOssService;
    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private PlayerCollectionService nftCollectionService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private PlayerAssetsDao playerAssetsDao;

    public NftAssetsDTO create(NftAssetsCreateRequest request) {

        GameUserContext context = GameUserContext.get();
        Long uid = context.getLong(TpulseHeaders.UID);

        //合集归属判定
        PlayerCollection collection = nftCollectionService.detail(uid, request.getCollectionId());
        if(null == collection || !collection.getUid().equals(uid)){
            throw BizException.newInstance(NftExchangeErrorCodes.MINT_EXCEPTION_INVALID_PARAMS);
        }

        Long assetsId = gsCollectionIdService.incrementAndGet(NftModules.NFT_ASSETS);
        //create
        NftAssets nftAssets = new NftAssets();
        nftAssets.setId(assetsId);
        nftAssets.setCreator(uid);
        nftAssets.setType(request.getType());
        nftAssets.setCollectionId(request.getCollectionId());
        String dir = request.getUrl().substring(request.getUrl().indexOf("tmp/") + 4, request.getUrl().lastIndexOf("/"));
        String url = gsOssService.changeDir(request.getUrl(), dir);
        if(!Strings.isNullOrEmpty(request.getPreviewUrl())){
            String previewDir = request.getUrl().substring(request.getUrl().indexOf("tmp/") + 4, request.getUrl().lastIndexOf("/"));
            String previewUrl = gsOssService.changeDir(request.getPreviewUrl(), previewDir);
            nftAssets.setPreviewUrl(previewUrl);
        }
        nftAssets.setUrl(url);
        nftAssets.setName(request.getName());
        nftAssets.setDesc(request.getDesc());
        nftAssets.setSupply(request.getSupply());
        nftAssets.setBlockchain(request.getBlockchain());
        nftAssets.setCreatedAt(System.currentTimeMillis());
        nftAssets.setUpdatedAt(System.currentTimeMillis());
        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_CREATE_IC.newBuilder()
                        .setAssets(NftAssetsDTO.toProto(nftAssets))
                        .build(),
                AssetsCreateRouteRequest.class
        ).getAssets();

        //insert relation
        PlayerAssets playerAssets = new PlayerAssets();
        playerAssets.setUid(uid);
        playerAssets.setAssetsId(assetsId);
        playerAssets.setCollectionId(collection.getCollectionId());
        playerAssets.setCreatedAt(System.currentTimeMillis());
        playerAssets.setUpdatedAt(playerAssets.getCreatedAt());
        playerAssetsDao.insert(playerAssets);

        //update quantity
        int count = (int)playerAssetsDao.count(PlayerAssetsQuery.newBuilder().uid(uid).setCollectionId(request.getCollectionId()).build());
        nftCollectionService.updateItems(uid, request.getCollectionId(), count);

        return NftAssetsDTO.from(assetsDTO);
    }


    public Page<NftAssetsDTO> list(NftAssetsListRequest request) {

        GameUserContext context = GameUserContext.get();
        Long uid = context.getLong(TpulseHeaders.UID);

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<PlayerAssets> dataPage = playerAssetsDao.findPage(
                PlayerAssetsQuery.newBuilder()
                        .uid(uid)
                        .setCollectionId(request.getCollectionId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build()
        );

        return new Page(
                0,
                dataPage.getData().stream().map(entity -> {

                    NftAssetsDTO dto = NftAssetsDTO.from(
                            routeClient.send(
                                    NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                            .setId(entity.getAssetsId())
                                            .build(),
                                    AssetsDetailRouteRequest.class
                            ).getAssets()
                    );

                    //current listing
                    NftExchange.NftAssetsProfileDTO exchangeProfile = routeClient.send(
                            NftExchange.ASSETS_EXCHANGE_PROFILE_IC.newBuilder()
                                    .setAssetsId(dto.getId())
                                    .build(),
                            AssetsExchangeProfileRouteRequest.class
                    ).getProfile();
                    if(exchangeProfile.hasCurrentListing()){
                        dto.setCurrentListing(NftAssetsDTO.ListingDTO.from(exchangeProfile.getCurrentListing()));
                    }

                    return dto;
                }).collect(Collectors.toList())
        );
    }


}
