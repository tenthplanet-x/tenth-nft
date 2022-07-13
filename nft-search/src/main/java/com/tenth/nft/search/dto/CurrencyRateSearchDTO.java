package com.tenth.nft.search.dto;

import com.tenth.nft.orm.marketplace.decoder.PricesToStringDecoder;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

import java.text.DecimalFormat;

/**
 * @author shijie
 */
public class CurrencyRateSearchDTO implements SimpleResponse {

    @SimpleField
    private String token;
    @SimpleField
    private String country;
    @SimpleField
    private String currency;
    @SimpleField(decoder = FloatToStringDecoder.class)
    private String rate;
    @SimpleField
    private Integer precision = 0 ;
    @SimpleField
    private String unit;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
