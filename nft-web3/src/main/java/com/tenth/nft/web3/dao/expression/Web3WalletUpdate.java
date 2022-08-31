package com.tenth.nft.web3.dao.expression;

import com.tenth.nft.web3.entity.Web3Wallet;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/18 15:53
 */
public class Web3WalletUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;
    @SimpleWriteParam
    private String walletAccountId;
    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam(name = "createdAt", opt = WriteOpt.SET_ON_INSERT)
    private Long createdAtOnInsert;
    @SimpleWriteParam
    private String blockchain;
    @SimpleWriteParam
    private String wallet;
    @SimpleWriteParam
    private String contractAddress;
    @SimpleWriteParam
    private Boolean contractApproved;
    @SimpleWriteParam(name = "walletAccountId", opt = WriteOpt.NULL)
    private Boolean walletAccountIdNull;
    @SimpleWriteParam(name = "wallet", opt = WriteOpt.NULL)
    private Boolean walletNull;
    @SimpleWriteParam(name = "contractAddress", opt = WriteOpt.NULL)
    private Boolean contractAddressNull;
    @SimpleWriteParam(name = "contractApproved", opt = WriteOpt.NULL)
    private Boolean contractApprovedNull;
    @SimpleWriteParam
    private Boolean wethContractApproved;
    @SimpleWriteParam
    private String wethContractAddress;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getUid(){
        return uid;
    }

    public String getWalletAccountId(){
        return walletAccountId;
    }

    public Long getCreatedAtOnInsert() {
        return createdAtOnInsert;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public String getWallet() {
        return wallet;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public Boolean getContractApproved() {
        return contractApproved;
    }

    public Boolean getWalletAccountIdNull() {
        return walletAccountIdNull;
    }

    public Boolean getWalletNull() {
        return walletNull;
    }

    public Boolean getContractAddressNull() {
        return contractAddressNull;
    }

    public Boolean getContractApprovedNull() {
        return contractApprovedNull;
    }

    public Boolean getWethContractApproved() {
        return wethContractApproved;
    }

    public String getWethContractAddress() {
        return wethContractAddress;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private Web3WalletUpdate update = new Web3WalletUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setWalletAccountId(String walletAccountId){
            update.walletAccountId = walletAccountId;
            return this;
        }

        public Web3WalletUpdate build(){
            return update;
        }

        public Builder setCreatedAtOnInsert() {
            update.createdAtOnInsert = System.currentTimeMillis();
            return this;
        }

        public Builder setBlockchain(String blockchain) {
            update.blockchain = blockchain;
            return this;
        }

        public Builder wallet(String wallet) {
            update.wallet = wallet;
            return this;
        }

        public Builder contractAddress(String contractAddress) {
            update.contractAddress = contractAddress;
            return this;
        }

        public Builder contractApproved(Boolean contractApproved) {
            update.contractApproved = contractApproved;
            return this;
        }

        public Builder walletNull() {
            update.walletNull = true;
            return this;
        }

        public Builder walletAccountIdNull() {
            update.walletAccountIdNull = true;
            return this;
        }

        public Builder contractAddressNull() {
            update.contractAddressNull = true;
            return this;
        }

        public Builder contractApprovedNull() {
            update.contractApprovedNull = true;
            return this;
        }

        public Builder wethContractAddress(String wethContractAddress) {
            update.wethContractAddress = wethContractAddress;
            return this;
        }

        public Builder wethContractApproved(Boolean wethContractApproved) {
            update.wethContractApproved = wethContractApproved;
            return this;
        }
    }

}
