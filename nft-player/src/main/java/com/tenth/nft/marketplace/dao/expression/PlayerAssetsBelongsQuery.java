package com.tenth.nft.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/29 11:51
 */
public class PlayerAssetsBelongsQuery extends SimplePageQuery {

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

        PlayerAssetsBelongsQuery query;

        public Builder() {
            super(new PlayerAssetsBelongsQuery());
            this.query = (PlayerAssetsBelongsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }
    }

}
