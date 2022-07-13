package com.tenth.nft.operation.dto;

import com.ruixi.tpulse.convention.orm.FloatToStringDecoder;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
public class CurrencyRateDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private String token;

    @SimpleField
    private String unit;

    @SimpleField
    private String currency;

    @SimpleField
    private String country;

    @SimpleField(decoder = FloatToStringDecoder.class)
    private String rate;

    @SimpleField
    private Integer precision;

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }
}
