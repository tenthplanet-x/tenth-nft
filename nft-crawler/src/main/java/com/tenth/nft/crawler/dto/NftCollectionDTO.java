package com.tenth.nft.crawler.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
public class NftCollectionDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private String name;

    @SimpleField
    private String desc;

    @SimpleField
    private String contractAddress;

    @SimpleField
    private String logoImage;

    @SimpleField
    private String featuredImage;

    @SimpleField
    private String bannerImage;

    @SimpleField
    private Float totalVolume;

    @SimpleField
    private Float floorPrice;

    @SimpleField
    private Integer totalSupply;

    @SimpleField
    private Long categoryId;

    @SimpleField
    private Long createdAt;

    public Long getId() {
        return id;
    }

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

    public void setId(Long id) {
        this.id = id;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
