package com.tenth.nft.crawler.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shijie
 * @createdAt 2022/6/15 14:00
 */
@Document("tpulse.nft_collection_stats")
@CompoundIndexes(@CompoundIndex(def = "{'contractAddress': 1, 'date': 1}"))
public class NftCollectionStats {

    @Id
    private Long id;

    private String date;

    private String contractAddress;

    private float oneDayVolume;

    private float sevenDayVolume;

    private float thirtyDayVolume;

    private float totalVolume;

    private Integer totalSupply;

    private Long createdAt;

    private Long updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public float getOneDayVolume() {
        return oneDayVolume;
    }

    public void setOneDayVolume(float oneDayVolume) {
        this.oneDayVolume = oneDayVolume;
    }

    public float getSevenDayVolume() {
        return sevenDayVolume;
    }

    public void setSevenDayVolume(float sevenDayVolume) {
        this.sevenDayVolume = sevenDayVolume;
    }

    public float getThirtyDayVolume() {
        return thirtyDayVolume;
    }

    public void setThirtyDayVolume(float thirtyDayVolume) {
        this.thirtyDayVolume = thirtyDayVolume;
    }

    public float getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(float totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Integer getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(Integer totalSupply) {
        this.totalSupply = totalSupply;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
