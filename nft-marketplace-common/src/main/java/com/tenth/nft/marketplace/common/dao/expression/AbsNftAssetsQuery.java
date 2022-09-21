package com.tenth.nft.marketplace.common.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class AbsNftAssetsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long collectionId;

    public Long getId() {
        return id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        AbsNftAssetsQuery query;

        public Builder() {
            super(new AbsNftAssetsQuery());
            this.query = (AbsNftAssetsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder setCollectionId(Long collectionId) {
            query.collectionId = collectionId;
            return this;
        }
    }

}
