package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 15:27
 */
public class NftSellQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftSellQuery query;

        public Builder() {
            super(new NftSellQuery());
            this.query = (NftSellQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

    }

}
