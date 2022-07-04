package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class NftCollectionQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long uid;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftCollectionQuery query;

        public Builder() {
            super(new NftCollectionQuery());
            this.query = (NftCollectionQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }
    }

}
