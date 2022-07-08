package com.tenth.nft.marketplace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruixi.tpulse.convention.orm.FloatToStringDecoder;
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

    private String creatorName;

    private boolean owned;

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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getOwners() {
        return owners;
    }

    public void setOwners(Integer owners) {
        this.owners = owners;
    }
}
