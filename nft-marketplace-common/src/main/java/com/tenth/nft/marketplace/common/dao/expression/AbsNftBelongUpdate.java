package com.tenth.nft.marketplace.common.dao.expression;

import com.tenth.nft.marketplace.common.entity.AbsNftBelong;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class AbsNftBelongUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Long owner;

    @SimpleWriteParam
    private Integer quantity;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createdAtOnInsert;

    @SimpleWriteParam(name = "quantity", opt = WriteOpt.INC)
    private Integer quantityInc;
    @SimpleWriteParam
    private Long collectionId;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public Long getOwner(){
        return owner;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public Long getCreatedAtOnInsert() {
        return createdAtOnInsert;
    }

    public Integer getQuantityInc() {
        return quantityInc;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private AbsNftBelongUpdate update = new AbsNftBelongUpdate();

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setOwner(Long owner){
            update.owner = owner;
            return this;
        }

        public Builder setQuantity(Integer quantity){
            update.quantity = quantity;
            return this;
        }

        public AbsNftBelongUpdate build(){
            return update;
        }

        public Builder createdAtOnInsert() {
            update.createdAtOnInsert = System.currentTimeMillis();
            return this;
        }

        public Builder quantityInc(Integer quantity) {
            update.quantityInc = quantity;
            return this;
        }


        public Builder setCollectionId(Long collectionId) {
            update.collectionId = collectionId;
            return this;
        }


    }

}
