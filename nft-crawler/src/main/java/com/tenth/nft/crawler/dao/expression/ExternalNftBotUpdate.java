package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
public class ExternalNftBotUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String description;

    @SimpleWriteParam
    private String contractAddress;

    @SimpleWriteParam
    private Long collectedAt;

    @SimpleWriteParam
    private String marketplace;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private String marketplaceId;

    @SimpleWriteParam
    private Boolean offline;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getDescription(){
        return description;
    }

    public String getContractAddress(){
        return contractAddress;
    }

    public Long getCollectedAt(){
        return collectedAt;
    }

    public String getMarketplace(){
        return marketplace;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public Boolean getOffline() {
        return offline;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private ExternalNftBotUpdate update = new ExternalNftBotUpdate();

        public Builder setDescription(String description){
            update.description = description;
            return this;
        }

        public Builder setContractAddress(String contractAddress){
            update.contractAddress = contractAddress;
            return this;
        }

        public Builder setCollectedAt(Long collectedAt){
            update.collectedAt = collectedAt;
            return this;
        }

        public Builder setMarketplace(String marketplace){
            update.marketplace = marketplace;
            return this;
        }

        public ExternalNftBotUpdate build(){
            return update;
        }

        public Builder setMarketplaceId(String marketplaceId) {
            update.marketplaceId = marketplaceId;
            return this;
        }

        public Builder setOffline(Boolean offline) {
            update.offline = offline;
            return this;
        }
    }

}
