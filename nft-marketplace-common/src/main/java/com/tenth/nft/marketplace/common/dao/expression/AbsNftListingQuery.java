package com.tenth.nft.marketplace.common.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;
import com.tpulse.gs.convention.dao.dto.Page;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class AbsNftListingQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long assetsId;
    @SimpleQueryParam
    private String seller;
    @SimpleQueryParam(name = "expireAt", opt = QueryOpt.LT)
    private Long expireAtLt;
    @SimpleQueryParam
    private Long collectionId;

    public Long getId() {
        return id;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public String getSeller() {
        return seller;
    }

    public Long getExpireAtLt() {
        return expireAtLt;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        AbsNftListingQuery query;

        public Builder() {
            super(new AbsNftListingQuery());
            this.query = (AbsNftListingQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }

        public Builder seller(String seller) {
            query.seller = seller;
            return this;
        }

        public Builder expireAtLt(Long expireAtLt) {
            query.expireAtLt = expireAtLt;
            return this;
        }

        public Builder collectionId(Long collectionId) {
            query.collectionId = collectionId;
            return this;
        }
    }

}
