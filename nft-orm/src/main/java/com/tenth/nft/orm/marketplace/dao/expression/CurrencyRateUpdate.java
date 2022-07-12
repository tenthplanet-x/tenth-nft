package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
public class CurrencyRateUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String token;

    @SimpleWriteParam
    private String unit;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private Float rate;

    @SimpleWriteParam
    private String country;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private Integer precision;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getToken(){
        return token;
    }

    public String getUnit(){
        return unit;
    }

    public String getCurrency(){
        return currency;
    }

    public Float getRate(){
        return rate;
    }

    public String getCountry() {
        return country;
    }

    public Integer getPrecision() {
        return precision;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private CurrencyRateUpdate update = new CurrencyRateUpdate();

        public Builder setToken(String token){
            update.token = token;
            return this;
        }

        public Builder setUnit(String unit){
            update.unit = unit;
            return this;
        }

        public Builder setCurrency(String currency){
            update.currency = currency;
            return this;
        }

        public Builder setRate(Float rate){
            update.rate = rate;
            return this;
        }

        public CurrencyRateUpdate build(){
            return update;
        }

        public Builder setCountry(String country) {
            update.country = country;
            return this;
        }

        public Builder setPrecision(Integer precision) {
            update.precision = precision;
            return this;
        }
    }

}
