package com.tenth.nft.crawler.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
public class ExternalNftBotDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private String description;

    @SimpleField
    private String contractAddress;

    @SimpleField
    private Long collectedAt;

    @SimpleField
    private String marketplace;

    @SimpleField
    private String marketplaceId;

    @SimpleField
    private Boolean offline;

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setContractAddress(String contractAddress){
        this.contractAddress = contractAddress;
    }

    public void setCollectedAt(Long collectedAt){
        this.collectedAt = collectedAt;
    }

    public void setMarketplace(String marketplace){
        this.marketplace = marketplace;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }
}
