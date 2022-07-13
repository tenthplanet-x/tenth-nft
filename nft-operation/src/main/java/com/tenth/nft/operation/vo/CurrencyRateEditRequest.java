package com.tenth.nft.operation.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/12 09:10
 */
@Valid
public class CurrencyRateEditRequest {

    @NotNull
    private Long id;

    private String token;

    private String unit;

    private String currency;

    private Float rate;

    private String country;

    private Integer precision = 0;

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

    public Float getRate(){
        return rate;
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
