package com.tenth.nft.web3.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/26 19:28
 */
public class Web3ContractApprovalUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String accountId;

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private String contractAddress;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getAccountId(){
        return accountId;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public String getContractAddress(){
        return contractAddress;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private Web3ContractApprovalUpdate update = new Web3ContractApprovalUpdate();

        public Builder setAccountId(String accountId){
            update.accountId = accountId;
            return this;
        }

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public Builder setContractAddress(String contractAddress){
            update.contractAddress = contractAddress;
            return this;
        }

        public Web3ContractApprovalUpdate build(){
            return update;
        }

    }

}
