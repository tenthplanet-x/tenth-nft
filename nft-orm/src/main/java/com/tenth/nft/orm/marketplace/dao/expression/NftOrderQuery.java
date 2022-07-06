package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class NftOrderQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftOrderQuery query;

        public Builder() {
            super(new NftOrderQuery());
            this.query = (NftOrderQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

    }

}