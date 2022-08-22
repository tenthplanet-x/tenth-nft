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
    }

}
