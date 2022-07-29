package com.tenth.nft.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/28 19:03
 */
public class PlayerAssetsUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private Long collectionId;

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getUid(){
        return uid;
    }

    public Long getCollectionId(){
        return collectionId;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private PlayerAssetsUpdate update = new PlayerAssetsUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
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

        public PlayerAssetsUpdate build(){
            return update;
        }

    }

}
