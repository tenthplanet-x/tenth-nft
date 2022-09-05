package com.tenth.nft.assets.service;

import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.AssetsRebuildRouteRequest;
import com.tenth.nft.convention.templates.BlockchainConfig;
import com.tenth.nft.convention.templates.BlockchainTemplate;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.web3.utils.HexAddresses;
import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.exchange.common.service.NftActivityService;
import com.tenth.nft.exchange.common.service.NftBelongService;
import com.tenth.nft.orm.marketplace.dao.NftAssetsNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsUpdate;
import com.tenth.nft.orm.marketplace.dto.NftAssetsDTO;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
import com.tenth.nft.orm.marketplace.entity.NftAssetsType;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.oss.IGsOssService;
import com.tpulse.gs.oss.qiniu.QiniuProperties;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 10:06
 */
@Service
public class NftAssetsService {

    @Autowired
    private NftAssetsNoCacheDao nftAssetsDao;
    @Autowired
    private IGsOssService gsOssService;
    @Autowired
    private QiniuProperties qiniuProperties;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private NftCollectionService nftCollectionService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    @Lazy
    private NftBelongService nftBelongService;
    @Autowired
    private NftActivityService nftActivityService;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    public NftMarketplace.ASSETS_CREATE_IS create(NftMarketplace.ASSETS_CREATE_IC _request) {

        NftMarketplace.AssetsDTO request = _request.getAssets();

        //create
        NftAssets nftAssets = new NftAssets();
        nftAssets.setId(request.getId());
        nftAssets.setCreator(request.getCreator());
        nftAssets.setType(NftAssetsType.valueOf(request.getType()));
        nftAssets.setCollectionId(request.getCollectionId());
        nftAssets.setUrl(request.getUrl());
        nftAssets.setPreviewUrl(request.getPreviewUrl());
        nftAssets.setName(request.getName());
        nftAssets.setDesc(request.getDesc());
        nftAssets.setSupply(request.getSupply());
        nftAssets.setBlockchain(request.getBlockchain());
        nftAssets.setCreatedAt(System.currentTimeMillis());
        nftAssets.setUpdatedAt(System.currentTimeMillis());
        nftAssets.setCreatorFeeRate(request.getCreatorFeeRate());
        nftAssets.setCreatorAddress(request.getCreatorAddress());
        nftAssets.setSignature(_request.getAssets().getSignature());
        nftAssets = nftAssetsDao.insert(nftAssets);

        //Init belong
        nftBelongService.create(
                request.getId(),
                request.getCreator(),
                request.getSupply()
        );
        nftActivityService.sendMintEvent(nftAssets);

        BlockchainConfig blockchainConfig = i18nGsTemplates.get(NftTemplateTypes.blockchain, BlockchainTemplate.class)
                .findOne(request.getBlockchain());
        nftAssets = nftAssetsDao.findAndModify(
                NftAssetsQuery.newBuilder().id(nftAssets.getId()).build(),
                NftAssetsUpdate.newBuilder()
                        .setContractAddress(blockchainConfig.getContractAddress())
                        .setTokenStandard(blockchainConfig.getTokenStandard())
                        .setToken(HexAddresses.of(nftAssets.getId()))
                        .build(),
                UpdateOptions.options().returnNew(true)
        );
        //Update collection stats
        nftCollectionService.bindAssets(request.getCollectionId(), nftAssets.getId());

        rebuildCache(nftAssets.getId());

        return NftMarketplace.ASSETS_CREATE_IS.newBuilder()
                .setAssets(NftAssetsDTO.toProto(nftAssets))
                .build();
    }


    public NftMarketplace.ASSETS_DETAIL_IS detail(NftMarketplace.ASSETS_DETAIL_IC request) {

        NftAssets dto = nftAssetsDao.findOne(NftAssetsQuery.newBuilder().id(request.getId()).build());
        return NftMarketplace.ASSETS_DETAIL_IS.newBuilder()
                .setAssets(NftAssetsDTO.toProto(dto))
                .build();
    }

    public void rebuildCache(Long assetsId){
        routeClient.send(
                NftSearch.NFT_ASSETS_REBUILD_IC.newBuilder()
                        .setAssetsId(assetsId)
                        .build(),
                AssetsRebuildRouteRequest.class
        );
    }

    public NftAssets findOne(Long assetsId) {
        SimpleQuery query = NftAssetsQuery.newBuilder().id(assetsId).build();
        return nftAssetsDao.findOne(query);
    }

    public void updateMintStatus(Long assetsId, TokenMintStatus status) {
        SimpleQuery query = NftAssetsQuery.newBuilder().id(assetsId).build();
        nftAssetsDao.update(
                query,
                NftAssetsUpdate.newBuilder()
                        .mintStatus(status)
                        .build()
        );
    }
}
