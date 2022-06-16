package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/15 14:07
 */
public class NftCollectionStatsUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String date;

    @SimpleWriteParam
    private String contractAddress;

    @SimpleWriteParam
    private Float oneDayVolume;

    @SimpleWriteParam
    private Float sevenDayVolume;

    @SimpleWriteParam
    private Float thirtyDayVolume;

    @SimpleWriteParam
    private Float totalVolume;

    @SimpleWriteParam
    private Integer totalSupply;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createdAtOnInsert;

    public Long getUpdatedAt() {
        return updatedAt;
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

    public Long getCreatedAtOnInsert() {
        return createdAtOnInsert;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftCollectionStatsUpdate update = new NftCollectionStatsUpdate();

        public Builder setDate(String date){
            update.date = date;
            return this;
        }

        public Builder setContractAddress(String contractAddress){
            update.contractAddress = contractAddress;
            return this;
        }

        public Builder setOneDayVolume(Float oneDayVolume){
            update.oneDayVolume = oneDayVolume;
            return this;
        }

        public Builder setSevenDayVolume(Float sevenDayVolume){
            update.sevenDayVolume = sevenDayVolume;
            return this;
        }

        public Builder setThirtyDayVolume(Float thirtyDayVolume){
            update.thirtyDayVolume = thirtyDayVolume;
            return this;
        }

        public Builder setTotalVolume(Float totalVolume){
            update.totalVolume = totalVolume;
            return this;
        }

        public Builder setTotalSupply(Integer totalSupply){
            update.totalSupply = totalSupply;
            return this;
        }

        public NftCollectionStatsUpdate build(){
            return update;
        }

        public Builder setCreatedAtOnInsert() {
            update.createdAtOnInsert = System.currentTimeMillis();
            return this;
        }
    }

}
