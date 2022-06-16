package com.tenth.nft.crawler.sdk.opensea.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shijie
 * @createdAt 2022/6/14 14:45
 */
public class OpenseaConstractDTO {

    private String address;

    @JsonProperty("created_date")
    private String createdAt; //2018-01-23T04:51:38.832339

    private String name;

    @JsonProperty("image_url")
    private String imageUrl;

    private OpenseaCollectionDTO collection;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public OpenseaCollectionDTO getCollection() {
        return collection;
    }

    public void setCollection(OpenseaCollectionDTO collection) {
        this.collection = collection;
    }
}
