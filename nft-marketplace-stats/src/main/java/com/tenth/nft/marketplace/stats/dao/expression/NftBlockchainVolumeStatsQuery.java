package com.tenth.nft.marketplace.stats.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/28 14:13
 */
public class NftBlockchainVolumeStatsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String timeDimension;
    @SimpleQueryParam
    private Long timestamp;
    @SimpleQueryParam
    private String blockchain;

    public Long getId() {
        return id;
    }

    public String getTimeDimension() {
        return timeDimension;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftBlockchainVolumeStatsQuery query;

        public Builder() {
            super(new NftBlockchainVolumeStatsQuery());
            this.query = (NftBlockchainVolumeStatsQuery) super.query;
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
    }

}
