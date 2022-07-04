package com.tenth.nft.crawler.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/15 14:07
 */
public class ExternalNftCollectionStatsDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private String date;

    @SimpleField
    private String contractAddress;

    @SimpleField
    private Float oneDayVolume;

    @SimpleField
    private Float sevenDayVolume;

    @SimpleField
    private Float thirtyDayVolume;

    @SimpleField
    private Float totalVolume;

    @SimpleField
    private Integer totalSupply;

    public Long getId() {
        return id;
    }

    public String getDate(){
        return date;
    }

    public String getContractAddress(){
        return contractAddress;
    }

    public Float getOneDayVolume(){
        return oneDayVolume;
    }

    public Float getSevenDayVolume(){
        return sevenDayVolume;
    }

    public Float getThirtyDayVolume(){
        return thirtyDayVolume;
    }

    public Float getTotalVolume(){
        return totalVolume;
    }

    public Integer getTotalSupply(){
        return totalSupply;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setContractAddress(String contractAddress){
        this.contractAddress = contractAddress;
    }

    public void setOneDayVolume(Float oneDayVolume){
        this.oneDayVolume = oneDayVolume;
    }

    public void setSevenDayVolume(Float sevenDayVolume){
        this.sevenDayVolume = sevenDayVolume;
    }

    public void setThirtyDayVolume(Float thirtyDayVolume){
        this.thirtyDayVolume = thirtyDayVolume;
    }

    public void setTotalVolume(Float totalVolume){
        this.totalVolume = totalVolume;
    }

    public void setTotalSupply(Integer totalSupply){
        this.totalSupply = totalSupply;
    }

}
