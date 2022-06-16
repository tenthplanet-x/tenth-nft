package com.tenth.nft.crawler.sdk.opensea.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shijie
 * @createdAt 2022/6/14 14:52
 */
public class OpenseaCollectionDTO {

    @JsonProperty("banner_image_url")
    private String bannerImageUrl;

    @JsonProperty("featured_image_url")
    private String featuredImageUrl;

    @JsonProperty("image_url")
    private String imageUrl;

    private OpenseaCollectionStats stats;

    private String name;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("description")
    private String desc;

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public OpenseaCollectionStats getStats() {
        return stats;
    }

    public void setStats(OpenseaCollectionStats stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
