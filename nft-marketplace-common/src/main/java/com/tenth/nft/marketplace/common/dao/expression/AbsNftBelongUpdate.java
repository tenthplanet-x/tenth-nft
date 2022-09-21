package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class NftBelongUpdate extends SimpleUpdate {

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

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftBelongUpdate update = new NftBelongUpdate();

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

        public NftBelongUpdate build(){
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

    }

}
