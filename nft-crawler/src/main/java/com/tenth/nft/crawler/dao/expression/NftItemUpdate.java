package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
public class NftItemUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String name;

    @SimpleWriteParam
    private String previewUrl;

    @SimpleWriteParam
    private String thumbnailUrl;

    @SimpleWriteParam
    private String rawUrl;

    @SimpleWriteParam
    private String contractAddress;

    @SimpleWriteParam
    private String tokenId;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createdAtOnInsert;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getName(){
        return name;
    }

    public String getPreviewUrl(){
        return previewUrl;
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    public String getRawUrl(){
        return rawUrl;
    }

    public String getContractAddress(){
        return contractAddress;
    }

    public String getTokenId(){
        return tokenId;
    }

    public Long getCreatedAtOnInsert() {
        return createdAtOnInsert;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftItemUpdate update = new NftItemUpdate();

        public Builder setName(String name){
            update.name = name;
            return this;
        }

        public Builder setPreviewUrl(String previewUrl){
            update.previewUrl = previewUrl;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl){
            update.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder setRawUrl(String rawUrl){
            update.rawUrl = rawUrl;
            return this;
        }

        public Builder setContractAddress(String contactAddress){
            update.contractAddress = contactAddress;
            return this;
        }

        public Builder setTokenId(String tokenId){
            update.tokenId = tokenId;
            return this;
        }

        public NftItemUpdate build(){
            return update;
        }

        public Builder setCreatedAtOnInsert() {
            update.createdAtOnInsert = System.currentTimeMillis();
            return this;
        }
    }

}
