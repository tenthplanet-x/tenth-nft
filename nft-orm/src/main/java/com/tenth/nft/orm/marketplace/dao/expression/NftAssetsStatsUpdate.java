package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/27 15:26
 */
public class NftAssetsStatsUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Float totalVolume;

    @SimpleWriteParam
    private Float floorPrice;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createAtOnInsert;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public Float getTotalVolume(){
        return totalVolume;
    }

    public Float getFloorPrice(){
        return floorPrice;
    }

    public String getCurrency(){
        return currency;
    }

    public Long getCreateAtOnInsert() {
        return createAtOnInsert;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftAssetsStatsUpdate update = new NftAssetsStatsUpdate();

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setTotalVolume(Float totalVolume){
            update.totalVolume = totalVolume;
            return this;
        }

        public Builder setFloorPrice(Float floorPrice){
            update.floorPrice = floorPrice;
            return this;
        }

        public Builder setCurrency(String currency){
            update.currency = currency;
            return this;
        }

        public NftAssetsStatsUpdate build(){
            return update;
        }

        public Builder createAtOnInsert() {
            update.createAtOnInsert = System.currentTimeMillis();
            return this;
        }
    }

}
