package com.tenth.nft.marketplace.common.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

import java.util.List;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class AbsNftAssetsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long collectionId;
    @SimpleQueryParam(name = "_id", opt = QueryOpt.IN)
    private List<Long> idIn;

    public Long getId() {
        return id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public List<Long> getIdIn() {
        return idIn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        AbsNftAssetsQuery query;

        public Builder() {
            super(new AbsNftAssetsQuery());
            this.query = (AbsNftAssetsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder setCollectionId(Long collectionId) {
            query.collectionId = collectionId;
            return this;
        }

        public Builder idIn(List<Long> idIn) {
            query.idIn = idIn;
            return this;
        }
    }

}
