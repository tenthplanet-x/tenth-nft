package com.tenth.nft.marketplace.stats.dto;

import com.tenth.nft.marketplace.common.dto.BigDecimalToStringDecoder;
import com.tenth.nft.marketplace.stats.entity.NftCollectionRecommendAssets;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

import java.util.List;


/**
 * @author shijie
 */
public class NftCollectionRecommendDTO implements SimpleResponse {

    @SimpleField
    private String blockchain;
    @SimpleField
    private Long collectionId;
    @SimpleField
    private String name;
    @SimpleField
    private String logoImage;
    @SimpleField
    private String featuredImage;
    @SimpleField
    private Integer items;
    @SimpleField
    private List<NftCollectionRecommendAssets> recommendAssets;
    @SimpleField(decoder = BigDecimalToStringDecoder.class)
    private String totalVolume;
    @SimpleField
    private String currency;

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

    public String getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(String totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
