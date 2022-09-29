package com.tenth.nft.marketplace.stats.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 18:58
 */
public class NftCollectionVolumeStatsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long collectionId;
    @SimpleQueryParam
    private String blockchain;
    @SimpleQueryParam
    private Long timestamp;
    @SimpleQueryParam
    private String timeDimension;

    public Long getId() {
        return id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getTimeDimension() {
        return timeDimension;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftCollectionVolumeStatsQuery query;

        public Builder() {
            super(new NftCollectionVolumeStatsQuery());
            this.query = (NftCollectionVolumeStatsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder timeDimension(String timeDimension) {
            query.timeDimension = timeDimension;
            return this;
        }

        public Builder timeStamp(Long timestamp) {
            query.timestamp = timestamp;
            return this;
        }

        public Builder blockchain(String blockchain) {
            query.blockchain = blockchain;
            return this;
        }

        public Builder collectionId(Long collectionId) {
            query.collectionId = collectionId;
            return this;
        }


    }

}
