package com.tenth.nft.marketplace.stats.dao.expression;

import com.tenth.nft.marketplace.common.dao.expression.AbsNftBelongQuery;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 18:58
 */
public class NftAssetsVolumeStatsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String timeDimension;
    @SimpleQueryParam
    private String blockchain;
    @SimpleQueryParam
    private Long collectionId;
    @SimpleQueryParam
    private Long assetsId;
    @SimpleQueryParam
    private Long timestamp;

    public Long getId() {
        return id;
    }

    public String getTimeDimension() {
        return timeDimension;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftAssetsVolumeStatsQuery query;

        public Builder() {
            super(new NftAssetsVolumeStatsQuery());
            this.query = (NftAssetsVolumeStatsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder timeDimension(String timeDimension) {
            query.timeDimension = timeDimension;
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

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }

        public Builder timeStamp(Long timestamp) {
            query.timestamp = timestamp;
            return this;
        }
    }

}
