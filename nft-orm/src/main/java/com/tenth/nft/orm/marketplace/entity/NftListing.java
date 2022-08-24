package com.tenth.nft.orm.marketplace.entity;

import com.tpulse.gs.convention.dao.annotation.SimpleCache;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shijie
 */
@Document("tpulse.nft_exchange_listing")
@SimpleCache(cacheField = "assetsId")
public class NftListing {

    @Id
    private Long id;

    private Long uid;

    @Indexed
    private Long assetsId;

    private Integer quantity;

    private String price;

    private String currency;

    private Long startAt;

    @Indexed
    private Long expireAt;

    private Long createdAt;

    private Long updatedAt;

    private Boolean canceled = false;

    private Long activityId;

    private Long creatorUid;

    private String creatorFeeRate;

    private String signature;

    private String payWalletAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Long getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(Long creatorUid) {
        this.creatorUid = creatorUid;
    }

    public String getCreatorFeeRate() {
        return creatorFeeRate;
    }

    public void setCreatorFeeRate(String creatorFeeRate) {
        this.creatorFeeRate = creatorFeeRate;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPayWalletAddress() {
        return payWalletAddress;
    }

    public void setPayWalletAddress(String payWalletAddress) {
        this.payWalletAddress = payWalletAddress;
    }
}
