package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class NftListingQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long assetsId;
    @SimpleQueryParam
    private Long uid;
    @SimpleQueryParam
    private Boolean canceled;
    @SimpleQueryParam(name = "expireAt", opt = QueryOpt.LT)
    private Long expireAtLt;

    public Long getId() {
        return id;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public Long getUid() {
        return uid;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public Long getExpireAtLt() {
        return expireAtLt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftListingQuery query;

        public Builder() {
            super(new NftListingQuery());
            this.query = (NftListingQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }

        public Builder uid(Long owner) {
            query.uid = owner;
            return this;
        }

        public Builder canceled(Boolean canceled) {
            query.canceled = canceled;
            return this;
        }

        public Builder expireAtLt(Long expireAtLt) {
            query.expireAtLt = expireAtLt;
            return this;
        }
    }

}
