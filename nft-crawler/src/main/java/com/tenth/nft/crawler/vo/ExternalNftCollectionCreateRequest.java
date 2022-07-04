package com.tenth.nft.crawler.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Valid
public class ExternalNftCollectionCreateRequest {

    private String name;

    private String desc;

    private String contractAddress;

    private String logoImage;

    private String featuredImage;

    private String bannerImage;

    private Float totalVolume;

    private Float floorPrice;

    private Integer totalSupply;

    private Long categoryId;

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public String getContractAddress(){
        return contractAddress;
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

    public Float getTotalVolume(){
        return totalVolume;
    }

    public Float getFloorPrice(){
        return floorPrice;
    }

    public Integer getTotalSupply(){
        return totalSupply;
    }

    public Long getCategoryId(){
        return categoryId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setContractAddress(String contractAddress){
        this.contractAddress = contractAddress;
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

    public void setTotalVolume(Float totalVolume){
        this.totalVolume = totalVolume;
    }

    public void setFloorPrice(Float floorPrice){
        this.floorPrice = floorPrice;
    }

    public void setTotalSupply(Integer totalSupply){
        this.totalSupply = totalSupply;
    }

    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }

}
