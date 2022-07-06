package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class NftMintUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private String tokenId;

    @SimpleWriteParam
    private String tokenStandard;

    @SimpleWriteParam
    private String contractAddress;

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public String getTokenId(){
        return tokenId;
    }

    public String getTokenStandard(){
        return tokenStandard;
    }

    public String getContractAddress(){
        return contractAddress;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftMintUpdate update = new NftMintUpdate();

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setTokenId(String tokenId){
            update.tokenId = tokenId;
            return this;
        }

        public Builder setTokenStandard(String tokenStandard){
            update.tokenStandard = tokenStandard;
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

        public NftMintUpdate build(){
            return update;
        }

    }

}
