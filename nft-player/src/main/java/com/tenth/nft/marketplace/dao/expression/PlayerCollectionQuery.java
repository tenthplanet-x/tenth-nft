package com.tenth.nft.marketplace.dao.expression;

import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/28 19:03
 */
public class PlayerCollectionQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long uid;
    @SimpleQueryParam
    private Long collectionId;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        PlayerCollectionQuery query;

        public Builder() {
            super(new PlayerCollectionQuery());
            this.query = (PlayerCollectionQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }

        public Builder collectionId(Long collectionId) {
            query.collectionId = collectionId;
            return this;
        }
    }

}
