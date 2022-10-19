package com.tenth.nft.marketplace.stats.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 15:52
 */
public class NftCollectionRecommendVersionQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftCollectionRecommendVersionQuery query;

        public Builder() {
            super(new NftCollectionRecommendVersionQuery());
            this.query = (NftCollectionRecommendVersionQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

    }

}
