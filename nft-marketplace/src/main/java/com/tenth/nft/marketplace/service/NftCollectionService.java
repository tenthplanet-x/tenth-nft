package com.tenth.nft.marketplace.service;

import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.orm.marketplace.dao.NftAssetsNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.NftCollectionNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionUpdate;
import com.tenth.nft.orm.marketplace.dto.NftCollectionDTO;
import com.tenth.nft.orm.marketplace.dto.NftCollectionDetailDTO;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        nftCollection.setCreatorFeeRate(request.getCreatorFeeRate());
        nftCollection.setBlockchain(request.getBlockchain());
        nftCollection.setItems(0);
        nftCollection = nftCollectionDao.insert(nftCollection);

        rebuild(nftCollection.getId());

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
        rebuild(collectionId);
    }

    public NftCollection detail(Long collectionId) {
        return nftCollectionDao.findOne(NftCollectionQuery.newBuilder().id(collectionId).build());
    }

    private void rebuild(Long collectionId) {
        routeClient.send(
                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
                        .setCollectionId(collectionId)
                        .build(),
                CollectionRebuildRouteRequest.class
        );
    }

}
