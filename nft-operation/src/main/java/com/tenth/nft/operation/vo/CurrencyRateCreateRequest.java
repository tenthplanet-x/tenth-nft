package com.tenth.nft.operation.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
@Valid
public class CurrencyRateCreateRequest {

    @NotEmpty
    private String token;
    @NotEmpty
    private String unit;
    @NotEmpty
    private String currency = "USA";
    @NotNull
    private Float rate;
    @NotEmpty
    private String country;
    @NotNull
    private Integer precision = 0;

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

    public void setToken(String token){
        this.token = token;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public void setRate(Float rate){
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
