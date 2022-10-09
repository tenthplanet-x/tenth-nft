package com.tenth.nft.marketplace.common.service;

import com.tenth.nft.marketplace.common.dao.AbsNftBelongDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBelongQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBelongUpdate;
import com.tenth.nft.marketplace.common.dto.NftAseetsOwnerDTO;
import com.tenth.nft.marketplace.common.dto.NftAssetsDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftBelong;
import com.tenth.nft.marketplace.common.vo.NftAssetsOwnRequest;
import com.tenth.nft.marketplace.common.vo.NftOwnerListRequest;
import com.tpulse.commons.biz.dto.PageRequest;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.dao.dto.Page;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
public abstract class AbsNftBelongService<T extends AbsNftBelong>{

    private AbsNftBelongDao<T> nftBelongDao;

    private AbsNftAssetsService nftAssetsService;

    public AbsNftBelongService(
            AbsNftBelongDao<T> nftBelongDao,
            AbsNftAssetsService nftAssetsService
    ) {
        this.nftBelongDao = nftBelongDao;
        this.nftAssetsService = nftAssetsService;
    }

    /**
     * Initialize owns
     * @param collectionId
     * @param assetsId
     * @param owner
     * @param quantity
     */
    public void init(Long collectionId, Long assetsId, String owner, int quantity) {

        T nftBelong = nftBelongDao.findAndModify(
                AbsNftBelongQuery.newBuilder()
                        .assetsId(assetsId)
                        .owner(owner)
                        .build(),
                AbsNftBelongUpdate.newBuilder()
                        .setCollectionId(collectionId)
                        .quantityInc(quantity)
                        .createdAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true).returnNew(true)
        );
        afterQuantityChange(nftBelong);
    }

    public void inc(Long assetsId, String owner, int quantity) {

        T nftBelong = nftBelongDao.findAndModify(
                AbsNftBelongQuery.newBuilder()
                        .assetsId(assetsId)
                        .owner(owner)
                        .build(),
                AbsNftBelongUpdate.newBuilder()
                        .quantityInc(quantity)
                        .createdAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true).returnNew(true)
        );
        afterQuantityChange(nftBelong);
    }

    public void dec(Long assetsId, String owner, int quantity) {

        T nftBelong = nftBelongDao.findAndModify(
                AbsNftBelongQuery.newBuilder()
                        .assetsId(assetsId)
                        .owner(owner)
                        .build(),
                AbsNftBelongUpdate.newBuilder()
                        .quantityInc(-quantity)
                        .createdAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true).returnNew(true)
        );
        afterQuantityChange(nftBelong);
    }

    protected abstract void afterQuantityChange(T nftBelong);

    public int owns(Long assetsId, String owner) {

        AbsNftBelong nftBelong = nftBelongDao.findOne(
                AbsNftBelongQuery.newBuilder()
                        .assetsId(assetsId)
                        .owner(owner)
                        .build()
        );

        if(null == nftBelong){
            return 0;
        }
        return nftBelong.getQuantity();

    }


    public <DTO extends NftAseetsOwnerDTO> Page<DTO> ownerList(NftOwnerListRequest request, Class<DTO> dtoClass) {

        Page<DTO> dtos = nftBelongDao.findPage(
                AbsNftBelongQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField("_id")
                        .setReverse(true)
                        .build(),
                dtoClass
        );

        return dtos;
    }

    public <DTO extends NftAssetsDTO> Page<DTO> myAssets(PageRequest request, String owner, Class<DTO> dtoClass) {

        Page<T> dataPage = nftBelongDao.findPage(
                AbsNftBelongQuery.newBuilder()
                        .owner(owner)
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField("_id")
                        .setReverse(true)
                        .build()
        );

        if(!dataPage.getData().isEmpty()){
            List<Long> assetsIds = dataPage.getData().stream().map(dto -> dto.getAssetsId()).collect(Collectors.toList());
            List<DTO> assets = nftAssetsService.findByIds(assetsIds, dtoClass);
            Map<Long, DTO> assetsMap = assets.stream().collect(Collectors.toMap(DTO::getId, Function.identity()));

            return new Page<>(
                    dataPage.getTotal(),
                    assetsIds.stream().map(id -> assetsMap.get(id)).filter(Objects::nonNull).collect(Collectors.toList())
            );
        }

        return new Page<>(0, null);
    }

    public int owners(Long assetsId) {
        return (int)nftBelongDao.count(AbsNftBelongQuery.newBuilder().assetsId(assetsId).build());
    }

    public List<T> ownerList(Long assetsId) {
        return nftBelongDao.find(AbsNftBelongQuery.newBuilder().assetsId(assetsId).build());
    }
}
