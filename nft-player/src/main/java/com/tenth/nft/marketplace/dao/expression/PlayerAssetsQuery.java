package com.tenth.nft.marketplace.dao.expression;

import com.tenth.nft.marketplace.entity.PlayerAssets;
import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/28 19:03
 */
public class PlayerAssetsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long collectionId;
    @SimpleQueryParam
    private Long uid;

    public Long getId() {
        return id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public Long getUid() {
        return uid;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        PlayerAssetsQuery query;

        public Builder() {
            super(new PlayerAssetsQuery());
            this.query = (PlayerAssetsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder setCollectionId(Long collectionId) {
            query.collectionId = collectionId;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }
    }

}
