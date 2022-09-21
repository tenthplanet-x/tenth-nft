package com.tenth.nft.marketplace.service;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.TpulseIdModules;
import com.tenth.nft.convention.OssPaths;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.marketplace.buildin.BuildInCollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.web3.Web3CollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.marketplace.vo.NftCollectionCreateRequest;
import com.tenth.nft.orm.marketplace.dto.NftCollectionDTO;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWeb3Wallet;
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
    private Web3Properties web3Properties;

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
        String logoImageDir = OssPaths.create(OssPaths.COLLECTION, id, "logo");
        String logoImageUrl = gsOssService.copyToPath(request.getLogoImage(), logoImageDir);
        String featuredImageDir = OssPaths.create(OssPaths.COLLECTION, id, "feature");
        String featuredImageUrl = gsOssService.copyToPath(request.getFeaturedImage(), featuredImageDir);

        NftMarketplace.CollectionDTO.Builder collectionDTOBuilder = NftMarketplace.CollectionDTO.newBuilder()
                .setCreator(String.valueOf(uid))
                .setName(request.getName())
                .setDesc(request.getDesc())
                .setBlockchain(request.getBlockchain())
                .setCreatorFeeRate(request.getCreatorFeeRate())
                .setLogoImage(logoImageUrl)
                .setFeaturedImage(featuredImageUrl)
                .setCreatedAt(System.currentTimeMillis());
        if(null != request.getCategory()){
            collectionDTOBuilder.setCategory(request.getCategory());
        }

        NftMarketplace.CollectionDTO collectionDTO = null;
        if(request.getBlockchain().equals(web3Properties.getBlockchain())){
            //web3
            String creatorAddress = routeClient.send(
                    NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                            .setUid(uid)
                            .setNeedBalance(false)
                            .build(),
                    Web3WalletBalanceRouteRequest.class
            ).getBalance().getAddress();
            collectionDTOBuilder.setCreator(creatorAddress);

            collectionDTO = routeClient.send(
                    NftMarketplace.COLLECTION_CREATE_IC.newBuilder()
                            .setCollection(collectionDTOBuilder.build())
                            .build(),
                    Web3CollectionCreateRouteRequest.class
            ).getCollection();
        }else{
            //buildIn
            collectionDTO = routeClient.send(
                    NftMarketplace.COLLECTION_CREATE_IC.newBuilder()
                            .setCollection(collectionDTOBuilder.build())
                            .build(),
                    BuildInCollectionCreateRouteRequest.class
            ).getCollection();
        }

        NftCollectionDTO nftCollectionDTO = toDTO(collectionDTO);
        return nftCollectionDTO;
    }

    private NftCollectionDTO toDTO(NftMarketplace.CollectionDTO collectionDTO) {

        NftCollectionDTO nftCollectionDTO = new NftCollectionDTO();

        nftCollectionDTO.setId(collectionDTO.getId());
        nftCollectionDTO.setUid(Long.valueOf(collectionDTO.getCreator()));
        if(collectionDTO.hasCategory()){
            nftCollectionDTO.setCategory(collectionDTO.getCategory());
        }
        nftCollectionDTO.setName(collectionDTO.getName());
        nftCollectionDTO.setDesc(Strings.emptyToNull(collectionDTO.getDesc()));
        nftCollectionDTO.setLogoImage(Strings.emptyToNull(collectionDTO.getLogoImage()));
        nftCollectionDTO.setFeaturedImage(Strings.emptyToNull(collectionDTO.getFeaturedImage()));
        nftCollectionDTO.setBlockchain(collectionDTO.getBlockchain());
        nftCollectionDTO.setCreatorFeeRate(collectionDTO.getCreatorFeeRate());
        nftCollectionDTO.setItems(collectionDTO.getItems());

        return nftCollectionDTO;

    }

}
