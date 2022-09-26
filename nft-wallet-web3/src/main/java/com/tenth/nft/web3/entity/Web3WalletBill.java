package com.tenth.nft.web3.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author shijie
 */
@Document("tpulse.web3_wallet_bill")
@CompoundIndex(def = "{'accountId': 1, 'blockchain': 1, 'transactionId': 1}")
@CompoundIndex(def = "{'accountId': 1, 'productCode': 1, 'outOrderId': 1}")
public class Web3WalletBill {

    @Id
    private Long id;

    private String blockchain;

    private String accountId;

    private Integer activityCfgId;

    private String productCode;

    private String productId;

    private String outOrderId;

    private String merchantType;

    private String merchantId;

    private String transactionId;

    private Long createdAt;

    private Long updatedAt;

    private String state;

    private Long expiredAt;

    private String currency;

    private String value;

    private String remark;

    private List<Web3WalletBillProfit> profits;

    private boolean notified;

    private int retry;

    private String usedGasValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getActivityCfgId() {
        return activityCfgId;
    }

    public void setActivityCfgId(Integer activityCfgId) {
        this.activityCfgId = activityCfgId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Long expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public List<Web3WalletBillProfit> getProfits() {
        return profits;
    }

    public void setProfits(List<Web3WalletBillProfit> profits) {
        this.profits = profits;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public String getUsedGasValue() {
        return usedGasValue;
    }

    public void setUsedGasValue(String usedGasValue) {
        this.usedGasValue = usedGasValue;
    }
}
