package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
public class CurrencyRateQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String token;
    @SimpleQueryParam
    private String country;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getCountry() {
        return country;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        CurrencyRateQuery query;

        public Builder() {
            super(new CurrencyRateQuery());
            this.query = (CurrencyRateQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder token(String token) {
            query.token = token;
            return this;
        }

        public Builder country(String country) {
            query.country = country;
            return this;
        }
    }

}
