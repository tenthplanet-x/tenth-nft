package com.tenth.nft.marketplace.common.service;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionDetailRouteRequest;
import com.tenth.nft.marketplace.common.dao.AbsNftCollectionDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftCollectionQuery;
import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionListRequest;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
public abstract class AbsNftCollectionService<T extends AbsNftCollection> {

    private AbsNftCollectionDao<T> nftCollectionDao;

    public AbsNftCollectionService(AbsNftCollectionDao<T> nftCollectionDao) {
        this.nftCollectionDao = nftCollectionDao;
    }

    /**
     * 创建合集
     * @param _request
     * @return
     */
    public NftMarketplace.COLLECTION_CREATE_IS create(NftMarketplace.COLLECTION_CREATE_IC _request) {

        NftMarketplace.CollectionDTO request = _request.getCollection();
        AbsNftCollection collection = insert(request);
        afterInsert(collection);
        NftMarketplace.CollectionDTO dto = toProto(collection);
        return NftMarketplace.COLLECTION_CREATE_IS.newBuilder()
                .setCollection(dto)
                .build();
    }

    /**
     * 获取合集详情
     * @param request
     * @return
     */
    public NftMarketplace.COLLECTION_DETAIL_IS detail(NftMarketplace.COLLECTION_DETAIL_IC request) {

        T dto = nftCollectionDao.findOne(AbsNftCollectionQuery.newBuilder()
                .id(request.getId())
                .build());
        NftMarketplace.CollectionDTO collectionDTO = toProto(dto);
        return NftMarketplace.COLLECTION_DETAIL_IS.newBuilder()
                .setCollection(collectionDTO)
                .build();
    }

    protected AbsNftCollection insert(NftMarketplace.CollectionDTO request) {

        T nftCollection = newCollection();

        nftCollection.setId(request.getId());
        nftCollection.setCreator(request.getCreator());
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
        return nftCollection;
    }

    protected abstract T newCollection();

    protected abstract void afterInsert(AbsNftCollection collection);

    protected static NftMarketplace.CollectionDTO toProto(AbsNftCollection nftCollection) {

        NftMarketplace.CollectionDTO.Builder builder = NftMarketplace.CollectionDTO.newBuilder();
        builder.setId(nftCollection.getId());
        builder.setCreator(nftCollection.getCreator());
        if(null != nftCollection.getCategory()){
            builder.setCategory(nftCollection.getCategory());
        }
        builder.setName(nftCollection.getName());
        if(!Strings.isNullOrEmpty(nftCollection.getDesc())){
            builder.setDesc(nftCollection.getDesc());
        }
        builder.setLogoImage(nftCollection.getLogoImage());
        if(!Strings.isNullOrEmpty(nftCollection.getFeaturedImage())){
            builder.setFeaturedImage(nftCollection.getFeaturedImage());
        }
        builder.setCreatedAt(nftCollection.getCreatedAt());
        builder.setBlockchain(nftCollection.getBlockchain());
        if(!Strings.isNullOrEmpty(nftCollection.getCreatorFeeRate())){
            builder.setCreatorFeeRate(nftCollection.getCreatorFeeRate());
        }
        builder.setItems(nftCollection.getItems());

        return builder.build();
    }

    public void updateAssetsCount(Long collectionId, Long assetsId) {
        throw new UnsupportedOperationException();
    }

    public <DTO extends NftCollectionDTO> Page<DTO> list(NftCollectionListRequest request, Optional<String> creator, Class<DTO> dtoClass) {

        String sortField = request.getSortField();
        boolean reverse = request.isReverse();
        if(Strings.isNullOrEmpty(sortField)){
            sortField = "_id";
            reverse = true;
        }

        Page<DTO> dataPage = nftCollectionDao.findPage(
                AbsNftCollectionQuery.newBuilder()
                        .creator(creator.orElse(null))
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField(sortField)
                        .setReverse(reverse)
                        .build(),
                dtoClass
        );

        return dataPage;

    }

    public <DTO extends NftCollectionDTO> DTO detail(NftCollectionDetailRequest request, Class<DTO> dtoClass) {
        return nftCollectionDao.findOne(
                AbsNftCollectionQuery.newBuilder().id(request.getId()).build(),
                dtoClass
        );
    }


    public T findById(Long collectionId) {
        return nftCollectionDao.findOne(
                AbsNftCollectionQuery.newBuilder().id(collectionId).build()
        );
    }
}
