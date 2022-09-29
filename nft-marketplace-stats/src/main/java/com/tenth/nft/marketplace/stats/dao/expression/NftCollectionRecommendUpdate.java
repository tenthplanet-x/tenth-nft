package com.tenth.nft.marketplace.stats.dao.expression;

import com.tenth.nft.marketplace.stats.entity.NftCollectionRecommendAssets;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 16:28
 */
public class NftCollectionRecommendUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String version;
    @SimpleWriteParam
    private Long categoryId;
    @SimpleWriteParam
    private String blockchain;
    @SimpleWriteParam
    private Long collectionId;
    @SimpleWriteParam
    private String name;
    @SimpleWriteParam
    private String logoImage;
    @SimpleWriteParam
    private String featuredImage;
    @SimpleWriteParam
    private Integer items;
    @SimpleWriteParam
    private List<NftCollectionRecommendAssets> recommendAssets;
    @SimpleWriteParam
    private BigDecimal totalVolume;
    @SimpleWriteParam
    private Double score;
    @SimpleWriteParam
    private String currency;
    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam
    private Double totalVolumeRate;
    @SimpleWriteParam
    private Double dateVolumeRate;
    @SimpleWriteParam
    private Long lastExchangedAt;
    @SimpleWriteParam
    private BigDecimal dateVolume;
    @SimpleWriteParam
    private Double dateRate;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getVersion(){
        return version;
    }

    public Long getCategoryId(){
        return categoryId;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public Long getCollectionId(){
        return collectionId;
    }

    public String getName(){
        return name;
    }

    public String getLogoImage(){
        return logoImage;
    }

    public String getFeaturedImage(){
        return featuredImage;
    }

    public Integer getItems(){
        return items;
    }

    public List<NftCollectionRecommendAssets> getRecommendAssets(){
        return recommendAssets;
    }

    public BigDecimal getTotalVolume(){
        return totalVolume;
    }

    public Double getScore(){
        return score;
    }

    public String getCurrency(){
        return currency;
    }

    public Double getTotalVolumeRate() {
        return totalVolumeRate;
    }

    public Double getDateVolumeRate() {
        return dateVolumeRate;
    }

    public Long getLastExchangedAt() {
        return lastExchangedAt;
    }

    public BigDecimal getDateVolume() {
        return dateVolume;
    }

    public Double getDateRate() {
        return dateRate;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftCollectionRecommendUpdate update = new NftCollectionRecommendUpdate();

        public Builder setVersion(String version){
            update.version = version;
            return this;
        }

        public Builder setCategoryId(Long categoryId){
            update.categoryId = categoryId;
            return this;
        }

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public Builder setCollectionId(Long collectionId){
            update.collectionId = collectionId;
            return this;
        }

        public Builder setName(String name){
            update.name = name;
            return this;
        }

        public Builder setLogoImage(String logoImage){
            update.logoImage = logoImage;
            return this;
        }

        public Builder setFeaturedImage(String featuredImage){
            update.featuredImage = featuredImage;
            return this;
        }

        public Builder setItems(Integer items){
            update.items = items;
            return this;
        }

        public Builder setRecommendAssets(List<NftCollectionRecommendAssets> recommendAssets){
            update.recommendAssets = recommendAssets;
            return this;
        }

        public Builder setTotalVolume(BigDecimal totalVolume){
            update.totalVolume = totalVolume;
            return this;
        }

        public Builder setCurrency(String currency){
            update.currency = currency;
            return this;
        }

        public Builder totalVolumeRate(Double totalVolumeRate) {
            update.totalVolumeRate = totalVolumeRate;
            return this;
        }

        public NftCollectionRecommendUpdate build(){
            return update;
        }

        public Builder dateVolumeRate(double dateVolumeRate) {
            update.dateVolumeRate = dateVolumeRate;
            return this;
        }

        public Builder lastExchangedAt(Long lastExchangedAt) {
            update.lastExchangedAt = lastExchangedAt;
            return this;
        }

        public Builder totalVolume(BigDecimal totalVolume) {
            update.totalVolume = totalVolume;
            return this;
        }

        public Builder dateVolume(BigDecimal dateVolume) {
            update.dateVolume = dateVolume;
            return this;
        }

        public Builder dateRate(Double dateRate) {
            update.dateRate = dateRate;
            return this;
        }

        public Builder score(Double score) {
            update.score = score;
            return this;
        }
    }

}
