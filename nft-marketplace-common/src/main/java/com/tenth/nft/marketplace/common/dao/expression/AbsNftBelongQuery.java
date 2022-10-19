package com.tenth.nft.marketplace.common.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class AbsNftBelongQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long assetsId;
    @SimpleQueryParam
    private String owner;

    public Long getId() {
        return id;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public String getOwner() {
        return owner;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        AbsNftBelongQuery query;

        public Builder() {
            super(new AbsNftBelongQuery());
            this.query = (AbsNftBelongQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }

        public Builder owner(String uid) {
            query.owner = uid;
            return this;
        }
    }

}
