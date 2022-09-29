package com.tenth.nft.marketplace.stats.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 16:25
 */
public class NftAssetsTaskProgressUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long lastTime;
    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createdAtOnInsert;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getLastTime(){
        return lastTime;
    }

    public Long getCreatedAtOnInsert() {
        return createdAtOnInsert;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftAssetsTaskProgressUpdate update = new NftAssetsTaskProgressUpdate();

        public Builder setLastTime(Long lastTime){
            update.lastTime = lastTime;
            return this;
        }

        public NftAssetsTaskProgressUpdate build(){
            return update;
        }

        public Builder createdAtOnInsert() {
            update.createdAtOnInsert = System.currentTimeMillis();
            return this;
        }
    }

}
