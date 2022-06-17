package com.tenth.nft.orm.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
public class NftCategoryQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftCategoryQuery query;

        public Builder() {
            super(new NftCategoryQuery());
            this.query = (NftCategoryQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

    }

}
