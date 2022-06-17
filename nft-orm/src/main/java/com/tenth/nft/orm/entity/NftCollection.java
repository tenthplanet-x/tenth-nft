package com.tenth.nft.orm.entity;

import com.tpulse.gs.convention.dao.annotation.SimpleCache;
import com.tpulse.gs.convention.dao.annotation.columns.SimpleColumnTypeText;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ChiShiJie
 * @createdAt 2022/6/14 11:21
 */
@Document("tenth.nft_collection")
@SimpleCache(cacheField = "_id")
public class NftCollection {

    @Id
    private Long id;

    private String name;

    @SimpleColumnTypeText
    private String desc;

    private String contractAddress;

    private String logoImage;

    private String featuredImage;

    private String bannerImage;

    private Long createdAt;

    private Long updatedAt;

    private Float totalVolume;

    private Float floorPrice;

    private Integer totalSupply;

    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
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

    public Float getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(Float floorPrice) {
        this.floorPrice = floorPrice;
    }

    public Integer getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(Integer totalSupply) {
        this.totalSupply = totalSupply;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
