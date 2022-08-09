package com.tenth.nft.marketplace.dao.expression;

import com.tenth.nft.orm.marketplace.dao.expression.NftCollectionUpdate;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/28 19:03
 */
public class PlayerCollectionUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private Long collectionId;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private Integer items;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getUid(){
        return uid;
    }

    public Integer getItems() {
        return items;
    }

    public Long getCollectionId(){
        return collectionId;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private PlayerCollectionUpdate update = new PlayerCollectionUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setCollectionId(Long collectionId){
            update.collectionId = collectionId;
            return this;
        }

        public PlayerCollectionUpdate build(){
            return update;
        }

        public Builder setItems(Integer items) {
            update.items = items;
            return this;
        }
    }

}
