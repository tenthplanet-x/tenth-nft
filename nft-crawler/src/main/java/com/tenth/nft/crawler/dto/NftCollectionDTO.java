package com.tenth.nft.crawler.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

import java.util.List;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
public class NftCollectionDTO implements SimpleResponse {

    @SimpleField
    private String name;

    @SimpleField
    private String logoImage;

    @SimpleField
    private String featuredImage;

    @SimpleField
    private String bannerImage;

    private List<NftItemDTO> items;

    @JsonIgnore(true)
    @SimpleField
    private String contractAddress;

    @SimpleField
    private Float totalVolume;

    public String getName(){
        return name;
    }

    public String getLogoImage(){
        return logoImage;
    }

    public String getFeaturedImage(){
        return featuredImage;
    }

    public String getBannerImage(){
        return bannerImage;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLogoImage(String logoImage){
        this.logoImage = logoImage;
    }

    public void setFeaturedImage(String featuredImage){
        this.featuredImage = featuredImage;
    }

    public void setBannerImage(String bannerImage){
        this.bannerImage = bannerImage;
    }

    public List<NftItemDTO> getItems() {
        return items;
    }

    public void setItems(List<NftItemDTO> items) {
        this.items = items;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Float getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Float totalVolume) {
        this.totalVolume = totalVolume;
    }
}
