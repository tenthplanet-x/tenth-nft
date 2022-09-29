package com.tenth.nft.marketplace.stats.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/27 15:52
 */
public class NftCollectionRecommendVersionUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String version;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getVersion(){
        return version;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftCollectionRecommendVersionUpdate update = new NftCollectionRecommendVersionUpdate();

        public Builder setVersion(String version){
            update.version = version;
            return this;
        }

        public NftCollectionRecommendVersionUpdate build(){
            return update;
        }

    }

}
