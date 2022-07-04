package com.tenth.nft.crawler.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:39
 */
@Valid
public class ExternalNftBotCreateRequest {

    @NotEmpty
    private String description;
    @NotEmpty
    private String contractAddress;
    @NotEmpty
    private String marketplace;
    @NotEmpty
    private String marketplaceId;

    public String getDescription(){
        return description;
    }

    public String getContractAddress(){
        return contractAddress;
    }

    public String getMarketplace(){
        return marketplace;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setContractAddress(String contractAddress){
        this.contractAddress = contractAddress;
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
}
