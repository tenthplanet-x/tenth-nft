package com.tenth.nft.orm.marketplace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.orm.FloatToStringDecoder;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class NftCollectionDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @JsonIgnore
    @SimpleField
    private String creator;

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

    private String creatorFeeRate;

    @SimpleField
    private String blockchain;

    @SimpleField
    private Integer items;

    private UserProfileDTO creatorProfile;

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

    public String getCreatorFeeRate() {
        return creatorFeeRate;
    }

    public void setCreatorFeeRate(String creatorFeeRate) {
        this.creatorFeeRate = creatorFeeRate;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public UserProfileDTO getCreatorProfile() {
        return creatorProfile;
    }

    public void setCreatorProfile(UserProfileDTO creatorProfile) {
        this.creatorProfile = creatorProfile;
    }


}
