package com.tenth.nft.marketplace.common.service;

import com.tenth.nft.marketplace.common.dao.AbsNftBelongDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBelongQuery;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftBelongUpdate;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tenth.nft.marketplace.common.entity.AbsNftBelong;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;

/**
 * @author shijie
 */
public abstract class AbsNftBelongService<T extends AbsNftBelong>{

    private AbsNftBelongDao<T> nftBelongDao;

    public AbsNftBelongService(AbsNftBelongDao<T> nftBelongDao) {
        this.nftBelongDao = nftBelongDao;
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
                UpdateOptions.options().returnNew(true)
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
                UpdateOptions.options().returnNew(true)
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
}
