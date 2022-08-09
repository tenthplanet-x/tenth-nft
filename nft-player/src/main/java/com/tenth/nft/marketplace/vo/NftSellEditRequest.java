package com.tenth.nft.marketplace.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 15:27
 */
@Valid
public class NftSellEditRequest {

    @NotNull
    private Long id;

    private Boolean fixedPrice;

    private String currency;

    private Float price;

    private Float startPrice;

    private Float reversePrice;

    private Float margin;

    private Long startTime;

    private Long endTime;

    public Long getId() {
        return id;
    }

    public Boolean getFixedPrice(){
        return fixedPrice;
    }

    public String getCurrency(){
        return currency;
    }

    public Float getPrice(){
        return price;
    }

    public Float getStartPrice(){
        return startPrice;
    }

    public Float getReversePrice(){
        return reversePrice;
    }

    public Float getMargin(){
        return margin;
    }

    public Long getStartTime(){
        return startTime;
    }

    public Long getEndTime(){
        return endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFixedPrice(Boolean fixedPrice){
        this.fixedPrice = fixedPrice;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public void setPrice(Float price){
        this.price = price;
    }

    public void setStartPrice(Float startPrice){
        this.startPrice = startPrice;
    }

    public void setReversePrice(Float reversePrice){
        this.reversePrice = reversePrice;
    }

    public void setMargin(Float margin){
        this.margin = margin;
    }

    public void setStartTime(Long startTime){
        this.startTime = startTime;
    }

    public void setEndTime(Long endTime){
        this.endTime = endTime;
    }
}
