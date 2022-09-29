package com.tenth.nft.marketplace.stats.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * @author shijie
 */
@Document("stats.nft_assets_volume_stats")
@CompoundIndexes(
        {
                @CompoundIndex(def = "{'collectionId': 1, 'timestamp':1}"),
                @CompoundIndex(def = "{'assetsId': 1, 'timestamp':1}")

        }
)
public class NftAssetsVolumeStats {

    @Id
    private Long id;

    private String timeDimension;

    @Indexed
    private Long timestamp;

    private String blockchain;

    private Long collectionId;

    private Long assetsId;

    private BigDecimal volume;

    private int exchanges;

    private Long lastExchangedAt;

    private Long createdAt;

    private Long updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeDimension() {
        return timeDimension;
    }

    public void setTimeDimension(String timeDimension) {
        this.timeDimension = timeDimension;
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

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public int getExchanges() {
        return exchanges;
    }

    public void setExchanges(int exchanges) {
        this.exchanges = exchanges;
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getLastExchangedAt() {
        return lastExchangedAt;
    }

    public void setLastExchangedAt(Long lastExchangedAt) {
        this.lastExchangedAt = lastExchangedAt;
    }
}
