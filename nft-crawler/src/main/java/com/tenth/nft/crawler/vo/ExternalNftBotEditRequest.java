package com.tenth.nft.crawler.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
@Valid
public class ExternalNftBotEditRequest {

    @NotNull
    private Long id;

    private String description;

    private String contractAddress;

    private Long collectedAt;

    private String marketplace;

    private String marketplaceId;

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
