package com.tenth.nft.marketplace.stats.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

import java.math.BigDecimal;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/28 14:13
 */
public class NftBlockchainVolumeStatsUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String timeDimension;

    @SimpleWriteParam
    private Long timestamp;

    @SimpleWriteParam
    private BigDecimal volume;

    @SimpleWriteParam
    private Integer exchanges;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createdAtOnInsert;
    @SimpleWriteParam(name = "volume", opt = WriteOpt.INC)
    private BigDecimal volumeInc;
    @SimpleWriteParam(name = "exchanges", opt = WriteOpt.INC)
    private Integer exchangesInc;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getTimeDimension(){
        return timeDimension;
    }

    public Long getTimestamp(){
        return timestamp;
    }

    public BigDecimal getVolume(){
        return volume;
    }

    public Integer getExchanges(){
        return exchanges;
    }

    public Long getCreatedAtOnInsert() {
        return createdAtOnInsert;
    }

    public BigDecimal getVolumeInc() {
        return volumeInc;
    }

    public Integer getExchangesInc() {
        return exchangesInc;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftBlockchainVolumeStatsUpdate update = new NftBlockchainVolumeStatsUpdate();

        public Builder setTimeDimension(String timeDimension){
            update.timeDimension = timeDimension;
            return this;
        }

        public Builder setTimestamp(Long timestamp){
            update.timestamp = timestamp;
            return this;
        }

        public Builder setVolume(BigDecimal volume){
            update.volume = volume;
            return this;
        }

        public Builder setExchanges(Integer exchanges){
            update.exchanges = exchanges;
            return this;
        }

        public NftBlockchainVolumeStatsUpdate build(){
            return update;
        }

        public Builder exchangesInc(Integer exchangesInc) {
            update.exchangesInc = exchangesInc;
            return this;
        }

        public Builder volumeInc(BigDecimal volumeInc) {
            update.volumeInc = volumeInc;
            return this;
        }

        public Builder createdAtOnInsert() {
            update.createdAtOnInsert = System.currentTimeMillis();
            return this;
        }
    }

}
