package com.tenth.nft.marketplace.common.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class AbsNftListingUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Integer quantity;

    @SimpleWriteParam
    private Float price;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private Long startAt;

    @SimpleWriteParam
    private Long expireAt;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private Boolean canceled;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public Float getPrice(){
        return price;
    }

    public String getCurrency(){
        return currency;
    }

    public Long getStartAt(){
        return startAt;
    }

    public Long getExpireAt(){
        return expireAt;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private AbsNftListingUpdate update = new AbsNftListingUpdate();

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setQuantity(Integer quantity){
            update.quantity = quantity;
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

        public Builder setStartAt(Long startAt){
            update.startAt = startAt;
            return this;
        }

        public Builder setExpireAt(Long expireAt){
            update.expireAt = expireAt;
            return this;
        }

        public AbsNftListingUpdate build(){
            return update;
        }

        public Builder canceled(Boolean canceled) {
            update.canceled = canceled;
            return this;
        }
    }

}
