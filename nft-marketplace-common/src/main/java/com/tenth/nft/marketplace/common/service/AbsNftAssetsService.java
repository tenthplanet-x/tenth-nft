package com.tenth.nft.marketplace.common.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.NftModules;
import com.tenth.nft.convention.OssPaths;
import com.tenth.nft.convention.templates.BlockchainConfig;
import com.tenth.nft.convention.templates.BlockchainTemplate;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.web3.utils.HexAddresses;
import com.tenth.nft.marketplace.common.dao.AbsNftAssetsDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftAssetsQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftAssetsUpdate;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.dto.NftAssetsDetailDTO;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.vo.NftAssetsCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsDetailRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsListRequest;
import com.tenth.nft.marketplace.common.vo.NftAssetsOwnRequest;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.oss.qiniu.service.QiniuOSSService;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
public abstract class AbsNftAssetsService<T extends AbsNftAssets> {

    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private QiniuOSSService qiniuOSSService;

    private AbsNftCollectionService nftCollectionService;

    private AbsNftAssetsDao<T> nftAssetsDao;

    private AbsNftBelongService nftBelongService;

    private AbsNftUbtLogService nftUbtLogService;

    private AbsNftListingService nftListingService;




    public AbsNftAssetsService(
            AbsNftAssetsDao<T> nftAssetsDao,
            AbsNftCollectionService nftCollectionService,
            AbsNftBelongService nftBelongService,
            AbsNftUbtLogService nftUbtLogService,
            AbsNftListingService nftListingService
            ) {
        this.nftAssetsDao = nftAssetsDao;
        this.nftCollectionService = nftCollectionService;
        this.nftBelongService = nftBelongService;
        this.nftUbtLogService = nftUbtLogService;
        this.nftListingService = nftListingService;
    }


    public NftAssetsDTO create(String creator, NftAssetsCreateRequest request) {

        beforeCreate(creator, request);

        //Create
        T nftAssets = buildEntity(creator, request);
        nftAssets = nftAssetsDao.insert(nftAssets);

        //Bind contract
        BlockchainConfig blockchainConfig = i18nGsTemplates.get(NftTemplateTypes.blockchain, BlockchainTemplate.class)
                .findOne(request.getBlockchain());
        nftAssets = nftAssetsDao.findAndModify(
                AbsNftAssetsQuery.newBuilder().id(nftAssets.getId()).build(),
                AbsNftAssetsUpdate.newBuilder()
                        .setContractAddress(blockchainConfig.getContractAddress())
                        .setTokenStandard(blockchainConfig.getTokenStandard())
                        .setToken(HexAddresses.of(nftAssets.getId()))
                        .build(),
                UpdateOptions.options().returnNew(true)
        );
        //Init belong
        nftBelongService.init(
                nftAssets.getCollectionId(),
                nftAssets.getId(),
                nftAssets.getCreator(),
                nftAssets.getSupply()
        );
        //Update collection stats
        nftCollectionService.updateAssetsCount(request.getCollectionId(), nftAssets.getId());
        //Create mint event
        nftUbtLogService.sendMintEvent(nftAssets);

        afterCreate(nftAssets);

        return nftAssetsDao.findOne(
                AbsNftAssetsQuery.newBuilder().id(nftAssets.getId()).build(),
                NftAssetsDTO.class
        );
    }

    protected void beforeCreate(String creator, NftAssetsCreateRequest request) {

        AbsNftCollection collection = nftCollectionService.findById(request.getCollectionId());
        if(!collection.getBlockchain().equals(request.getBlockchain())){
            throw BizException.newInstance(NftExchangeErrorCodes.CREATE_EXCEPTION_ILEGAL_BLOCKCHAIN);
        }
    }

    public NftMarketplace.ASSETS_DETAIL_IS detail(NftMarketplace.ASSETS_DETAIL_IC request) {

        T dto = nftAssetsDao.findOne(AbsNftAssetsQuery.newBuilder().id(request.getId()).build());
        return NftMarketplace.ASSETS_DETAIL_IS.newBuilder()
                .setAssets(toProto(dto))
                .build();
    }

    public <DTO extends NftAssetsDTO> Page<DTO> list(NftAssetsListRequest request, Class<DTO> dtoClass){

        Page<DTO> page = nftAssetsDao.findPage(
            AbsNftAssetsQuery.newBuilder()
                    .setCollectionId(request.getCollectionId())
                    .setPage(request.getPage())
                    .setPageSize(request.getPageSize())
                    .setSortField(request.getSortField())
                    .setReverse(request.isReverse())
                    .build(),
                dtoClass
        );
        if(!page.getData().isEmpty()){
            page.getData().forEach(dto -> {
                dto.setUnionId(getUnionId(dto.getId()));
                dto.setCollectionUnionId(getUnionId(dto.getCollectionId()));
                fillWithCurrentListing(dto, dto.getId());
            });
        }

        return page;
    }

    protected abstract String getUnionId(Long id);

    public <DTO extends NftAssetsDetailDTO> DTO detail(NftAssetsDetailRequest request, Class<DTO> dtoClass){

        //TODO current listing

        DTO nftAssetsDTO = nftAssetsDao.findOne(
                AbsNftAssetsQuery.newBuilder().id(request.getAssetsId()).build(),
                dtoClass
        );
        nftAssetsDTO.setUnionId(getUnionId(nftAssetsDTO.getId()));
        nftAssetsDTO.setCollectionUnionId(getUnionId(nftAssetsDTO.getCollectionId()));
        nftAssetsDTO.setOwners(nftBelongService.owners(request.getAssetsId()));

        fillWithCurrentListing(nftAssetsDTO, request.getAssetsId());
        return nftAssetsDTO;
    }

    protected <DTO extends NftAssetsDTO> void fillWithCurrentListing(DTO nftAssetsDTO, Long assetsId){
        NftListingDTO _listingDTO = nftListingService.getCurrentListing(assetsId);
        if(null != _listingDTO){
            NftAssetsDTO.ListingDTO listingDTO = new NftAssetsDTO.ListingDTO();
            listingDTO.setId(_listingDTO.getId());
            listingDTO.setCurrency(_listingDTO.getCurrency());
            listingDTO.setPrice(_listingDTO.getPrice());
            listingDTO.setExpireAt(_listingDTO.getExpireAt());
            listingDTO.setQuantity(_listingDTO.getQuantity());
            listingDTO.setStartAt(_listingDTO.getStartAt());
            listingDTO.setSellerProfile(_listingDTO.getSellerProfile());
            nftAssetsDTO.setCurrentListing(listingDTO);
        }
    }

    public T findOne(Long assetsId) {
        return nftAssetsDao.findOne(
                AbsNftAssetsQuery.newBuilder().id(assetsId).build()
        );
    }

    protected T buildEntity(String creator, NftAssetsCreateRequest request) {

        AbsNftCollection collection = nftCollectionService.findById(request.getCollectionId());

        T nftAssets = newNftAssets();
        nftAssets.setCollectionId(collection.getId());
        Long assetsId = gsCollectionIdService.incrementAndGet(NftModules.NFT_ASSETS);
        String previewUrl = request.getPreviewUrl();
        if(!Strings.isNullOrEmpty(previewUrl)){
            String previewPath = OssPaths.create(OssPaths.COLLECTION, assetsId, "preview");
            previewUrl = qiniuOSSService.copyToPath(previewUrl, previewPath);
        }

        nftAssets.setId(assetsId);
        nftAssets.setType(request.getType());
        nftAssets.setCollectionId(request.getCollectionId());
        nftAssets.setUrl(request.getUrl());
        nftAssets.setPreviewUrl(previewUrl);
        nftAssets.setName(request.getName());
        nftAssets.setDesc(request.getDesc());
        nftAssets.setSupply(request.getSupply());
        nftAssets.setBlockchain(request.getBlockchain());
        nftAssets.setCreatedAt(System.currentTimeMillis());
        nftAssets.setUpdatedAt(System.currentTimeMillis());
        nftAssets.setCreatorFeeRate(collection.getCreatorFeeRate());
        nftAssets.setCreator(creator);
        return nftAssets;
    }

    protected abstract T newNftAssets();

    protected abstract void afterCreate(T nftAssets);

    protected NftMarketplace.AssetsDTO toProto(T nftAssets) {
        NftMarketplace.AssetsDTO.Builder builder = NftMarketplace.AssetsDTO.newBuilder();
        builder.setId(nftAssets.getId());
        if(null != nftAssets.getType()){
            builder.setType(nftAssets.getType().name());
        }
        builder.setCollectionId(nftAssets.getCollectionId());
        builder.setUrl(nftAssets.getUrl());
        if(!Strings.isNullOrEmpty(nftAssets.getPreviewUrl())){
            builder.setPreviewUrl(nftAssets.getPreviewUrl());
        }
        builder.setName(nftAssets.getName());
        if(!Strings.isNullOrEmpty(nftAssets.getDesc())){
            builder.setDesc(nftAssets.getDesc());
        }
        builder.setSupply(nftAssets.getSupply());
        builder.setCreatedAt(nftAssets.getCreatedAt());
        builder.setBlockchain(nftAssets.getBlockchain());
        builder.setCreator(nftAssets.getCreator());
        if(!Strings.isNullOrEmpty(nftAssets.getCreatorFeeRate())){
            builder.setCreatorFeeRate(nftAssets.getCreatorFeeRate());
        }
//        if(!Strings.isNullOrEmpty(nftAssets.getSignature())){
//            builder.setSignature(nftAssets.getSignature());
//        }

        if(null != nftAssets.getContractAddress()){
            builder.setContractAddress(nftAssets.getContractAddress());
            builder.setTokenStandard(nftAssets.getTokenStandard());
            builder.setToken(nftAssets.getToken());
        }

//        if(TokenMintStatus.SUCCESS.equals(nftAssets.getMintStatus())){
//            builder.setMint(true);
//        }

        return builder.build();
    }


    public Long totalByCollectionId(Long collectionId) {
        return nftAssetsDao.count(AbsNftAssetsQuery.newBuilder().setCollectionId(collectionId).build());
    }

    public NftMarketplace.ASSETS_DETAIL_BATCH_IS batchDetail(NftMarketplace.ASSETS_DETAIL_BATCH_IC request){

        return NftMarketplace.ASSETS_DETAIL_BATCH_IS.newBuilder()
                .addAllAssets(
                        nftAssetsDao.find(
                                AbsNftAssetsQuery.newBuilder().idIn(request.getAssetsIdList()).build()
                        ).stream().map(this::toProto).collect(Collectors.toList())
                )
                .build();

    }


    public <DTO extends NftAssetsDTO> List<DTO> findByIds(List<Long> assetsIds, Class<DTO> dtoClass) {

        return nftAssetsDao.find(
                AbsNftAssetsQuery.newBuilder()
                        .idIn(assetsIds)
                        .build(),
                dtoClass
        );
    }
}
