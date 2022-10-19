package com.tenth.nft.marketplace.common.service;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.TpulseHeaders;
import com.ruixi.tpulse.convention.TpulseIdModules;
import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.OssPaths;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionDetailRouteRequest;
import com.tenth.nft.marketplace.common.dao.AbsNftCollectionDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftCollectionQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftCollectionUpdate;
import com.tenth.nft.marketplace.common.dto.NftCollectionDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.vo.NftCollectionCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionListRequest;
import com.tenth.nft.marketplace.common.vo.NftCollectionOwnListRequest;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.commons.biz.dto.PageRequest;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.oss.IGsOssService;
import com.tpulse.gs.oss.qiniu.service.QiniuOSSService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
public abstract class AbsNftCollectionService<T extends AbsNftCollection> {

    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private QiniuOSSService qiniuOSSService;

    private AbsNftCollectionDao<T> nftCollectionDao;

    private AbsNftAssetsService nftAssetsService;

    public AbsNftCollectionService(AbsNftCollectionDao<T> nftCollectionDao, AbsNftAssetsService nftAssetsService) {
        this.nftCollectionDao = nftCollectionDao;
        this.nftAssetsService = nftAssetsService;
    }

    /**
     * 创建合集
     * @param request
     * @return
     */
    public <DTO extends NftCollectionDTO> DTO create(String creator, NftCollectionCreateRequest request, Class<DTO> dtoClass) {

        AbsNftCollection collection = insert(creator, request);
        afterInsert(collection);

        NftCollectionDetailRequest detailRequest = new NftCollectionDetailRequest();
        detailRequest.setId(collection.getId());
        return detail(detailRequest, dtoClass);

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

    protected AbsNftCollection insert(String creator, NftCollectionCreateRequest request) {

        T nftCollection = newCollection();
        Long id = gsCollectionIdService.incrementAndGet(TpulseIdModules.COLLECTION);
        String logoImageDir = OssPaths.create(OssPaths.COLLECTION, id, "logo");
        String logoImageUrl = qiniuOSSService.copyToPath(request.getLogoImage(), logoImageDir);
        String featuredImageDir = OssPaths.create(OssPaths.COLLECTION, id, "feature");
        String featuredImageUrl = qiniuOSSService.copyToPath(request.getFeaturedImage(), featuredImageDir);

        nftCollection.setId(id);
        nftCollection.setCreator(creator);
        nftCollection.setCreatedAt(System.currentTimeMillis());
        nftCollection.setUpdatedAt(System.currentTimeMillis());
        nftCollection.setName(request.getName());
        nftCollection.setDesc(request.getDesc());
        nftCollection.setLogoImage(logoImageUrl);
        nftCollection.setFeaturedImage(featuredImageUrl);
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
        //throw new UnsupportedOperationException();

        Long items = nftAssetsService.totalByCollectionId(collectionId);
        nftCollectionDao.update(
                AbsNftCollectionQuery.newBuilder().id(collectionId).build(),
                AbsNftCollectionUpdate.newBuilder().items(items).build()
        );
    }

    public <DTO extends NftCollectionDTO> Page<DTO> list(PageRequest request, Optional<String> creator, Class<DTO> dtoClass) {

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
        if(!dataPage.getData().isEmpty()){
            dataPage.getData().stream().forEach(dto -> {
                dto.setUnionId(getUnionId(dto.getId()));
            });
        }

        return dataPage;

    }

    protected abstract String getUnionId(Long id);


    public <DTO extends NftCollectionDTO> DTO detail(NftCollectionDetailRequest request, Class<DTO> dtoClass) {
        DTO dto = nftCollectionDao.findOne(
                AbsNftCollectionQuery.newBuilder().id(request.getId()).build(),
                dtoClass
        );
        dto.setUnionId(getUnionId(dto.getId()));
        return dto;
    }


    public T findById(Long collectionId) {
        return nftCollectionDao.findOne(
                AbsNftCollectionQuery.newBuilder().id(collectionId).build()
        );
    }

    public List<T> findBatchById(List<Long> collectionIds) {
        return nftCollectionDao.find(
                AbsNftCollectionQuery.newBuilder().idIn(collectionIds).build()
        );
    }
}
