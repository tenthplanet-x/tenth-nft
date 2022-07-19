package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/18 08:24
 */
public class NftOfferUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private Float price;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private Long expireAt;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public Long getUid(){
        return uid;
    }

    public Float getPrice(){
        return price;
    }

    public String getCurrency(){
        return currency;
    }

    public Long getExpireAt(){
        return expireAt;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftOfferUpdate update = new NftOfferUpdate();

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setPrice(Float price){
            update.price = price;
            return this;
        }

        public Builder setCurrency(String currency){
            update.currency = currency;
            return this;
        }

        public Builder setExpireAt(Long expireAt){
            update.expireAt = expireAt;
            return this;
        }

        public NftOfferUpdate build(){
            return update;
        }

    }

}
