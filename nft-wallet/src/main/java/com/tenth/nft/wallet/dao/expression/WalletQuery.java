package com.tenth.nft.wallet.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/08 19:17
 */
public class WalletQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long uid;
    @SimpleQueryParam
    private String currency;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }

    public String getCurrency() {
        return currency;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        WalletQuery query;

        public Builder() {
            super(new WalletQuery());
            this.query = (WalletQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }

        public Builder currency(String currency) {
            query.currency = currency;
            return this;
        }
    }

}
