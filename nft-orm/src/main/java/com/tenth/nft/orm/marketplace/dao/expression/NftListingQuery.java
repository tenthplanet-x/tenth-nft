package com.tenth.nft.orm.marketplace.dao.expression;

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
    }

}
