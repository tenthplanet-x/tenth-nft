package com.tenth.nft.web3.dao.expression;

import com.tenth.nft.web3.entity.Web3WalletBill;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/19 12:00
 */
public class Web3WalletBillUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private String accountId;

    @SimpleWriteParam
    private Integer activityCfgId;

    @SimpleWriteParam
    private String productCode;

    @SimpleWriteParam
    private String productId;

    @SimpleWriteParam
    private String outOrderId;

    @SimpleWriteParam
    private String merchantType;

    @SimpleWriteParam
    private String merchantId;

    @SimpleWriteParam
    private String transactionId;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private String state;

    @SimpleWriteParam
    private String remark;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public String getAccountId(){
        return accountId;
    }

    public Integer getActivityCfgId(){
        return activityCfgId;
    }

    public String getProductCode(){
        return productCode;
    }

    public String getProductId(){
        return productId;
    }

    public String getOutOrderId(){
        return outOrderId;
    }

    public String getMerchantType(){
        return merchantType;
    }

    public String getMerchantId(){
        return merchantId;
    }

    public String getTransactionId(){
        return transactionId;
    }

    public String getState() {
        return state;
    }

    public String getRemark() {
        return remark;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private Web3WalletBillUpdate update = new Web3WalletBillUpdate();

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public Builder setAccountId(String accountId){
            update.accountId = accountId;
            return this;
        }

        public Builder setActivityCfgId(Integer activityCfgId){
            update.activityCfgId = activityCfgId;
            return this;
        }

        public Builder setProductCode(String productCode){
            update.productCode = productCode;
            return this;
        }

        public Builder setProductId(String productId){
            update.productId = productId;
            return this;
        }

        public Builder setOutOrderId(String outOrderId){
            update.outOrderId = outOrderId;
            return this;
        }

        public Builder setMerchantType(String merchantType){
            update.merchantType = merchantType;
            return this;
        }

        public Builder setMerchantId(String merchantId){
            update.merchantId = merchantId;
            return this;
        }

        public Builder setTransactionId(String transactionId){
            update.transactionId = transactionId;
            return this;
        }

        public Builder setState(String state) {
            update.state = state;
            return this;
        }

        public Web3WalletBillUpdate build(){
            return update;
        }

        public Builder setRemark(String remark) {
            update.remark = remark;
            return this;
        }
    }

}
