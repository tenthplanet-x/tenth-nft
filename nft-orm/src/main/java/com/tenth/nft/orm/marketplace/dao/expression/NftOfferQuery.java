package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/18 08:24
 */
public class NftOfferQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long uid;
    @SimpleQueryParam
    private Long assetsId;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftOfferQuery query;

        public Builder() {
            super(new NftOfferQuery());
            this.query = (NftOfferQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }
    }

}
