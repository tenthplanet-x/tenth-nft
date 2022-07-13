package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class NftContractUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private Long collectionId;

    @SimpleWriteParam
    private String contractAddress;

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private String tokenStandard;

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

    public String getContractAddress(){
        return contractAddress;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public String getTokenStandard(){
        return tokenStandard;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftContractUpdate update = new NftContractUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setCollectionId(Long collectionId){
            update.collectionId = collectionId;
            return this;
        }

        public Builder setContractAddress(String contractAddress){
            update.contractAddress = contractAddress;
            return this;
        }

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public Builder setTokenStandard(String tokenStandard){
            update.tokenStandard = tokenStandard;
            return this;
        }

        public NftContractUpdate build(){
            return update;
        }

    }

}
