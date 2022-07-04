package com.tenth.nft.marketplace.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@Valid
public class NftCollectionEditRequest {

    @NotNull
    private Long id;

    private Long uid;

    private String name;

    private String desc;

    private String logoImage;

    private String featuredImage;

    private Long category;

    private Float creatorFee;

    private String blockchain;

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

    public Float getCreatorFee() {
        return creatorFee;
    }

    public void setCreatorFee(Float creatorFee) {
        this.creatorFee = creatorFee;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }
}
