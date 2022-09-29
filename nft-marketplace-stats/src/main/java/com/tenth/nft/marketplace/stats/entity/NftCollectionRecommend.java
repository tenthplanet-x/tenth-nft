package com.tenth.nft.marketplace.stats.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author shijie
 */
@Document("stats.nft_collection_recommend")
@CompoundIndexes({
        @CompoundIndex(def = "{'version': 1, 'categoryId': 1, 'score': 1}"),
        @CompoundIndex(def = "{'version': 1, 'score': 1}")
})
public class NftCollectionRecommend {

    @Id
    public Long id;

    private String version;

    private Long categoryId;

    private String blockchain;

    private Long collectionId;

    private String name;

    private String logoImage;

    private String featuredImage;

    private Integer items;

    private List<NftCollectionRecommendAssets> recommendAssets;

    private Double score;

    private String currency;

    private Double totalVolumeRate;

    private Double dateVolumeRate;

    private BigDecimal dateVolume;

    private BigDecimal totalVolume;

    private Double dateRate;

    private Long lastExchangedAt;

    private Long createdAt;

    private Long updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
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

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public List<NftCollectionRecommendAssets> getRecommendAssets() {
        return recommendAssets;
    }

    public void setRecommendAssets(List<NftCollectionRecommendAssets> recommendAssets) {
        this.recommendAssets = recommendAssets;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTotalVolumeRate() {
        return totalVolumeRate;
    }

    public void setTotalVolumeRate(Double totalVolumeRate) {
        this.totalVolumeRate = totalVolumeRate;
    }

    public Double getDateVolumeRate() {
        return dateVolumeRate;
    }

    public void setDateVolumeRate(Double dateVolumeRate) {
        this.dateVolumeRate = dateVolumeRate;
    }

    public BigDecimal getDateVolume() {
        return dateVolume;
    }

    public void setDateVolume(BigDecimal dateVolume) {
        this.dateVolume = dateVolume;
    }

    public Double getDateRate() {
        return dateRate;
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

    public Long getLastExchangedAt() {
        return lastExchangedAt;
    }

    public void setLastExchangedAt(Long lastExchangedAt) {
        this.lastExchangedAt = lastExchangedAt;
    }

    public void setDateRate(Double dateRate) {
        this.dateRate = dateRate;
    }
}
