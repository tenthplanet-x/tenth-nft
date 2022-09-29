package com.tenth.nft.marketplace.stats.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 16:25
 */
public class NftAssetsExchangeLogUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private Long collectionId;

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Integer quantity;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private String price;

    @SimpleWriteParam
    private Long exchangedAt;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public Long getCollectionId(){
        return collectionId;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public String getCurrency(){
        return currency;
    }

    public String getPrice(){
        return price;
    }

    public Long getExchangedAt(){
        return exchangedAt;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftAssetsExchangeLogUpdate update = new NftAssetsExchangeLogUpdate();

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public Builder setCollectionId(Long collectionId){
            update.collectionId = collectionId;
            return this;
        }

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setQuantity(Integer quantity){
            update.quantity = quantity;
            return this;
        }

        public Builder setCurrency(String currency){
            update.currency = currency;
            return this;
        }

        public Builder setPrice(String price){
            update.price = price;
            return this;
        }

        public Builder setExchangedAt(Long exchangedAt){
            update.exchangedAt = exchangedAt;
            return this;
        }

        public NftAssetsExchangeLogUpdate build(){
            return update;
        }

    }

}
