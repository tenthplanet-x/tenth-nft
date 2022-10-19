package com.tenth.nft.marketplace.stats.dao.expression;

import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 16:28
 */
public class NftCollectionRecommendQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long categoryId;
    @SimpleQueryParam
    private String version;
    @SimpleQueryParam
    private Long collectionId;
    @SimpleQueryParam
    private String blockchain;

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getVersion() {
        return version;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftCollectionRecommendQuery query;

        public Builder() {
            super(new NftCollectionRecommendQuery());
            this.query = (NftCollectionRecommendQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder categoryId(Long categoryId) {
            query.categoryId = categoryId;
            return this;
        }

        public Builder version(String version) {
            query.version = version;
            return this;
        }

        public Builder collectionId(Long collectionId) {
            query.collectionId = collectionId;
            return this;
        }

        public Builder blockchain(String blockchain) {
            query.blockchain = blockchain;
            return this;
        }
    }

}
