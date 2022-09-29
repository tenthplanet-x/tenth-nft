package com.tenth.nft.marketplace.stats.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * @author shijie
 */
@Document("stats.nft_assets_exchange_log")
@CompoundIndexes(
        {
                @CompoundIndex(def = "{'collectionId': 1, 'createdAt': 1}"),
                @CompoundIndex(def = "{'assetsId': 1, 'createdAt': 1}"),
                @CompoundIndex(def = "{'blockchain': 1, 'createdAt': 1}")
        }
)
public class NftAssetsExchangeLog {

    @Id
    private Long id;

    private String blockchain;

    private Long collectionId;

    private Long assetsId;

    private Long quantity;

    private BigDecimal price;

    private Long exchangedAt;

    private Long createdAt;

    private Long updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getExchangedAt() {
        return exchangedAt;
    }

    public void setExchangedAt(Long exchangedAt) {
        this.exchangedAt = exchangedAt;
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
}
