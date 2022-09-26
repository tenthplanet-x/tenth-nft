package com.tenth.nft.marketplace.common.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/18 08:24
 */
public class AbsNftOfferUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private String buyer;

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

    public String getBuyer() {
        return buyer;
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

        private AbsNftOfferUpdate update = new AbsNftOfferUpdate();

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setBuyer(String buyer){
            update.buyer = buyer;
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

        public AbsNftOfferUpdate build(){
            return update;
        }

    }

}
