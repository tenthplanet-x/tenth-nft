package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

import java.util.List;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class NftAssetsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long collectionId;
    @SimpleQueryParam(name = "_id", opt = QueryOpt.IN)
    private List<Long> ids;

    public Long getId() {
        return id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftAssetsQuery query;

        public Builder() {
            super(new NftAssetsQuery());
            this.query = (NftAssetsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder setCollectionId(Long collectionId) {
            query.collectionId = collectionId;
            return this;
        }

        public Builder IdIn(List<Long> ids) {
            query.ids = ids;
            return this;
        }
    }

}
