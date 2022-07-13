package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 15:27
 */
public class NftSellUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Boolean fixedPrice;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private Float price;

    @SimpleWriteParam
    private Float startPrice;

    @SimpleWriteParam
    private Float reversePrice;

    @SimpleWriteParam
    private Float margin;

    @SimpleWriteParam
    private Long startTime;

    @SimpleWriteParam
    private Long endTime;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getFixedPrice(){
        return fixedPrice;
    }

    public String getCurrency(){
        return currency;
    }

    public Float getPrice(){
        return price;
    }

    public Float getStartPrice(){
        return startPrice;
    }

    public Float getReversePrice(){
        return reversePrice;
    }

    public Float getMargin(){
        return margin;
    }

    public Long getStartTime(){
        return startTime;
    }

    public Long getEndTime(){
        return endTime;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftSellUpdate update = new NftSellUpdate();

        public Builder setFixedPrice(Boolean fixedPrice){
            update.fixedPrice = fixedPrice;
            return this;
        }

        public Builder setCurrency(String currency){
            update.currency = currency;
            return this;
        }

        public Builder setPrice(Float price){
            update.price = price;
            return this;
        }

        public Builder setStartPrice(Float startPrice){
            update.startPrice = startPrice;
            return this;
        }

        public Builder setReversePrice(Float reversePrice){
            update.reversePrice = reversePrice;
            return this;
        }

        public Builder setMargin(Float margin){
            update.margin = margin;
            return this;
        }

        public Builder setStartTime(Long startTime){
            update.startTime = startTime;
            return this;
        }

        public Builder setEndTime(Long endTime){
            update.endTime = endTime;
            return this;
        }

        public NftSellUpdate build(){
            return update;
        }

    }

}
