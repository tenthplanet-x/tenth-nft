package com.tenth.nft.wallet.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/08 19:17
 */
public class WalletSettingQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long uid;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        WalletSettingQuery query;

        public Builder() {
            super(new WalletSettingQuery());
            this.query = (WalletSettingQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }
    }

}
