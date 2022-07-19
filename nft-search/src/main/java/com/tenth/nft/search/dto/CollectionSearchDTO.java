package com.tenth.nft.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

import java.util.List;


/**
 * @author shijie
 */
public class CollectionSearchDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @JsonIgnore
    @SimpleField
    private Long uid;

    @SimpleField
    private String name;

    @SimpleField
    private String desc;

    @SimpleField
    private String logoImage;

    @SimpleField
    private String featuredImage;

    @SimpleField
    private Long category;

    @SimpleField(decoder = FloatToStringDecoder.class)
    private String creatorFee;

    @SimpleField
    private String blockchain;

    @SimpleField
    private Integer items;

    private Integer owners = 1;

    private boolean owned;

    private String creatorName;

    private UserProfileDTO creatorProfile;

    private List<AssetsSearchDTO> recommendAssets;

    private String totalVolume;

    private String currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getCreatorFee() {
        return creatorFee;
    }

    public void setCreatorFee(String creatorFee) {
        this.creatorFee = creatorFee;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public Integer getOwners() {
        return owners;
    }

    public void setOwners(Integer owners) {
        this.owners = owners;
    }

    public UserProfileDTO getCreatorProfile() {
        return creatorProfile;
    }

    public void setCreatorProfile(UserProfileDTO creatorProfile) {
        this.creatorProfile = creatorProfile;
    }

    public List<AssetsSearchDTO> getRecommendAssets() {
        return recommendAssets;
    }

    public void setRecommendAssets(List<AssetsSearchDTO> recommendAssets) {
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
