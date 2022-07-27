package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/27 15:26
 */
public class NftAssetsStatsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long assetsId;

    public Long getId() {
        return id;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftAssetsStatsQuery query;

        public Builder() {
            super(new NftAssetsStatsQuery());
            this.query = (NftAssetsStatsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }
    }

}
