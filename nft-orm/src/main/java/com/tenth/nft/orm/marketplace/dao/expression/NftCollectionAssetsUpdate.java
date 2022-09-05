package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/05 16:18
 */
public class NftCollectionAssetsUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long collectionId;

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
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

        private NftCollectionAssetsUpdate update = new NftCollectionAssetsUpdate();

        public Builder setCollectionId(Long collectionId){
            update.collectionId = collectionId;
            return this;
        }

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public NftCollectionAssetsUpdate build(){
            return update;
        }

    }

}
