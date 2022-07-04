package com.tenth.nft.marketplace.entity;

import com.tpulse.gs.convention.dao.annotation.columns.SimpleColumnTypeText;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shijie
 */
@Document("tpulse.nft_collection")
public class NftCollection {

    @Id
    private Long id;

    @Indexed
    private Long uid;

    private String name;

    @SimpleColumnTypeText
    private String desc;

    private String logoImage;

    private String featuredImage;

    private Long createdAt;

    private Long updatedAt;

    private Long category;

    private float creatorFee;

    private String blockchain;

    private int items;

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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public float getCreatorFee() {
        return creatorFee;
    }

    public void setCreatorFee(float creatorFee) {
        this.creatorFee = creatorFee;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }
}
