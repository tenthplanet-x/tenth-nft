package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.marketplace.entity.NftAssetsType;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class NftAssetsUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private NftAssetsType type;

    @SimpleWriteParam
    private Long collectionId;

    @SimpleWriteParam
    private String url;

    @SimpleWriteParam
    private String previewUrl;

    @SimpleWriteParam
    private String name;

    @SimpleWriteParam
    private String desc;

    @SimpleWriteParam
    private Integer supply;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam
    private String blockchain;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public NftAssetsType getType(){
        return type;
    }

    public Long getCollectionId(){
        return collectionId;
    }

    public String getUrl(){
        return url;
    }

    public String getPreviewUrl(){
        return previewUrl;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public Integer getSupply(){
        return supply;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftAssetsUpdate update = new NftAssetsUpdate();

        public Builder setType(NftAssetsType type){
            update.type = type;
            return this;
        }

        public Builder setCollectionId(Long collectionId){
            update.collectionId = collectionId;
            return this;
        }

        public Builder setUrl(String url){
            update.url = url;
            return this;
        }

        public Builder setPreviewUrl(String previewUrl){
            update.previewUrl = previewUrl;
            return this;
        }

        public Builder setName(String name){
            update.name = name;
            return this;
        }

        public Builder setDesc(String desc){
            update.desc = desc;
            return this;
        }

        public Builder setSupply(Integer supply){
            update.supply = supply;
            return this;
        }

        public NftAssetsUpdate build(){
            return update;
        }

        public Builder setBlockchain(String blockchain) {
            update.blockchain = blockchain;
            return this;
        }
    }

}
