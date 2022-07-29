package com.tenth.nft.marketplace.service;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.TpulseIdModules;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.convention.routes.exchange.CollectionsExchangeProfileRouteRequest;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.marketplace.vo.*;
import com.tenth.nft.orm.marketplace.dao.NftAssetsNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.NftCollectionNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionUpdate;
import com.tenth.nft.orm.marketplace.dto.NftCollectionDTO;
import com.tenth.nft.orm.marketplace.dto.NftCollectionDetailDTO;
import com.tenth.nft.orm.marketplace.entity.NftAssets;
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
public class NftCollectionService {

    @Autowired
    private NftCollectionNoCacheDao nftCollectionDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftAssetsNoCacheDao nftAssetsNoCacheDao;

    public NftMarketplace.COLLECTION_CREATE_IS create(NftMarketplace.COLLECTION_CREATE_IC _request) {

        NftMarketplace.CollectionDTO request = _request.getCollection();
        NftCollection nftCollection = new NftCollection();
        nftCollection.setId(request.getId());
        nftCollection.setUid(request.getCreator());
        nftCollection.setCreatedAt(System.currentTimeMillis());
        nftCollection.setUpdatedAt(System.currentTimeMillis());
        nftCollection.setName(request.getName());
        nftCollection.setDesc(request.getDesc());
        nftCollection.setLogoImage(request.getLogoImage());
        nftCollection.setFeaturedImage(request.getFeaturedImage());
        nftCollection.setCategory(request.getCategory());
        nftCollection.setCreatorFee(request.getCreatorFee());
        nftCollection.setBlockchain(request.getBlockchain());
        nftCollection.setItems(0);
        nftCollection = nftCollectionDao.insert(nftCollection);

        routeClient.send(
                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
                        .setCollectionId(request.getId())
                        .build(),
                CollectionRebuildRouteRequest.class
        );

        NftMarketplace.CollectionDTO dto = NftCollectionDetailDTO.toProto(nftCollection);
        return NftMarketplace.COLLECTION_CREATE_IS.newBuilder()
                .setCollection(dto)
                .build();
    }

    public NftMarketplace.COLLECTION_DETAIL_IS detail(NftMarketplace.COLLECTION_DETAIL_IC request) {

        NftCollection dto = nftCollectionDao.findOne(NftCollectionQuery.newBuilder()
                .id(request.getId())
                .build());

        NftMarketplace.CollectionDTO collectionDTO = NftCollectionDTO.toProto(dto);

        return NftMarketplace.COLLECTION_DETAIL_IS.newBuilder()
                .setCollection(collectionDTO)
                .build();
    }

    public void updateItems(Long collectionId, long items) {
        nftCollectionDao.update(
                NftCollectionQuery.newBuilder().id(collectionId).build(),
                NftCollectionUpdate.newBuilder().items(items).build()
        );
    }

    public NftCollection detail(Long collectionId) {
        return nftCollectionDao.findOne(NftCollectionQuery.newBuilder().id(collectionId).build());
    }

}
