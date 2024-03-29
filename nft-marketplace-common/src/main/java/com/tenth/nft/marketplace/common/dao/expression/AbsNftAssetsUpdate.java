package com.tenth.nft.marketplace.common.dao.expression;

import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.marketplace.common.entity.NftAssetsType;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class AbsNftAssetsUpdate extends SimpleUpdate {

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
    @SimpleWriteParam
    private String contractAddress;
    @SimpleWriteParam
    private String tokenStandard;
    @SimpleWriteParam
    private String token;

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

    public String getContractAddress() {
        return contractAddress;
    }

    public String getTokenStandard() {
        return tokenStandard;
    }

    public String getToken() {
        return token;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private AbsNftAssetsUpdate update = newUpdate();

        protected AbsNftAssetsUpdate newUpdate() {
            return new AbsNftAssetsUpdate();
        }

        private String contractAddress;

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

        public AbsNftAssetsUpdate build(){
            return update;
        }

        public Builder setBlockchain(String blockchain) {
            update.blockchain = blockchain;
            return this;
        }

        public Builder setContractAddress(String contractAddress) {
            update.contractAddress = contractAddress;
            return this;
        }

        public Builder setTokenStandard(String tokenStandard) {
            update.tokenStandard = tokenStandard;
            return this;
        }

        public Builder setToken(String token) {
            update.token = token;
            return this;
        }


    }

}
