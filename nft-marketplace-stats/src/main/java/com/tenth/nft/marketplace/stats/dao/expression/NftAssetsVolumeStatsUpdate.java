package com.tenth.nft.marketplace.stats.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

import java.math.BigDecimal;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 18:58
 */
public class NftAssetsVolumeStatsUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String timeDimension;

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private Long collectionId;

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private BigDecimal volume;

    @SimpleWriteParam
    private Integer exchanges;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam(name = "exchanges", opt = WriteOpt.INC)
    private Integer exchangesInc;

    @SimpleWriteParam(name = "volume", opt = WriteOpt.INC)
    private BigDecimal volumeInc;

    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createdAtOnInsert;
    @SimpleWriteParam
    private Long lastExchangedAt;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getTimeDimension(){
        return timeDimension;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public Long getCollectionId(){
        return collectionId;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public BigDecimal getVolume(){
        return volume;
    }

    public Integer getExchanges(){
        return exchanges;
    }

    public Integer getExchangesInc() {
        return exchangesInc;
    }

    public BigDecimal getVolumeInc() {
        return volumeInc;
    }

    public Long getCreatedAtOnInsert() {
        return createdAtOnInsert;
    }

    public Long getLastExchangedAt() {
        return lastExchangedAt;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftAssetsVolumeStatsUpdate update = new NftAssetsVolumeStatsUpdate();

        public Builder setTimeDimension(String timeDimension){
            update.timeDimension = timeDimension;
            return this;
        }

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public Builder setCollectionId(Long collectionId){
            update.collectionId = collectionId;
            return this;
        }

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
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

        public NftAssetsVolumeStatsUpdate build(){
            return update;
        }

        public Builder exchangesInc(Integer exchanges) {
            update.exchangesInc = exchanges;
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

        public Builder lastExchangedAt(Long lastExchangedAt) {
            update.lastExchangedAt = lastExchangedAt;
            return this;
        }
    }

}
