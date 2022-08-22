package com.tenth.nft.web3.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/19 12:00
 */
public class Web3WalletEventUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private String accountId;

    @SimpleWriteParam
    private Long billId;

    @SimpleWriteParam
    private String transaction;

    @SimpleWriteParam
    private Integer activityCfgId;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private String value;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public String getAccountId(){
        return accountId;
    }

    public Long getBillId(){
        return billId;
    }

    public String getTransaction(){
        return transaction;
    }

    public Integer getActivityCfgId(){
        return activityCfgId;
    }

    public String getCurrency(){
        return currency;
    }

    public String getValue(){
        return value;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private Web3WalletEventUpdate update = new Web3WalletEventUpdate();

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public Builder setAccountId(String accountId){
            update.accountId = accountId;
            return this;
        }

        public Builder setBillId(Long billId){
            update.billId = billId;
            return this;
        }

        public Builder setTransaction(String transaction){
            update.transaction = transaction;
            return this;
        }

        public Builder setActivityCfgId(Integer activityCfgId){
            update.activityCfgId = activityCfgId;
            return this;
        }

        public Builder setCurrency(String currency){
            update.currency = currency;
            return this;
        }

        public Builder setValue(String value){
            update.value = value;
            return this;
        }

        public Web3WalletEventUpdate build(){
            return update;
        }

    }

}
