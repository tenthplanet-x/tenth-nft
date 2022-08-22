package com.tenth.nft.orm.marketplace.entity;

import com.tpulse.gs.convention.dao.annotation.SimpleCache;
import com.tpulse.gs.convention.dao.annotation.columns.SimpleColumnTypeText;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shijie
 */
@Document("tpulse.nft_assets")
@SimpleCache(cacheField = "_id")
public class NftAssets {

    @Id
    private Long id;

    private NftAssetsType type;

    @Indexed
    private Long collectionId;

    private String url;

    private String previewUrl;

    private String name;

    @SimpleColumnTypeText
    private String desc;

    private Integer supply;

    private Long createdAt;

    private Long updatedAt;

    private String blockchain;

    private String contractAddress;

    private String tokenStandard;

    private String token;

    private Long creator;

    private String creatorFeeRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public NftAssetsType getType() {
        return type;
    }

    public void setType(NftAssetsType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
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

    public Integer getSupply() {
        return supply;
    }

    public void setSupply(Integer supply) {
        this.supply = supply;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getTokenStandard() {
        return tokenStandard;
    }

    public void setTokenStandard(String tokenStandard) {
        this.tokenStandard = tokenStandard;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getCreatorFeeRate() {
        return creatorFeeRate;
    }

    public void setCreatorFeeRate(String creatorFeeRate) {
        this.creatorFeeRate = creatorFeeRate;
    }
}
