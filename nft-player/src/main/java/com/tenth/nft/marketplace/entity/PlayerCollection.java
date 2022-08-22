package com.tenth.nft.marketplace.entity;

import com.tpulse.gs.convention.dao.annotation.SimpleCache;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shijie
 */
@Document("tpulse.nft_player_collection")
@SimpleCache(cacheField = "uid")
public class PlayerCollection {

    @Id
    private Long id;

    @Indexed
    private Long uid;

    private Long collectionId;

    private Integer items = 0;

    private Long createdAt;

    private Long updatedAt;

    private String creatorFeeRate;

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

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
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

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public String getCreatorFeeRate() {
        return creatorFeeRate;
    }

    public void setCreatorFeeRate(String creatorFeeRate) {
        this.creatorFeeRate = creatorFeeRate;
    }
}
