package com.tenth.nft.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/29 11:51
 */
public class PlayerAssetsBelongsUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Long owns;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createdAtOnInsert;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getUid(){
        return uid;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public Long getOwns(){
        return owns;
    }

    public Long getCreatedAtOnInsert() {
        return createdAtOnInsert;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private PlayerAssetsBelongsUpdate update = new PlayerAssetsBelongsUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setOwns(Long owns){
            update.owns = owns;
            return this;
        }

        public PlayerAssetsBelongsUpdate build(){
            return update;
        }

        public Builder createdAtOnInsert() {
            update.createdAtOnInsert = System.currentTimeMillis();
            return this;
        }
    }

}
