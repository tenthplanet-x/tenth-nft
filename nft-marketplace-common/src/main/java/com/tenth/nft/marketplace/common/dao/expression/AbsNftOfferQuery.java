package com.tenth.nft.marketplace.common.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/18 08:24
 */
public class AbsNftOfferQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String buyer;
    @SimpleQueryParam
    private Long assetsId;
    @SimpleQueryParam(name = "expireAt", opt = QueryOpt.LT)
    private Long expireAtLt;

    public Long getId() {
        return id;
    }

    public String getBuyer() {
        return buyer;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public Long getExpireAtLt() {
        return expireAtLt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        AbsNftOfferQuery query;

        public Builder() {
            super(new AbsNftOfferQuery());
            this.query = (AbsNftOfferQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }

        public Builder buyer(String buyer) {
            query.buyer = buyer;
            return this;
        }

        public Builder expireAtLt(Long expireAtLt) {
            query.expireAtLt = expireAtLt;
            return this;
        }
    }

}
