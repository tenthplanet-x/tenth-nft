package com.tenth.nft.web3.dto;

import com.tenth.nft.web3.entity.Web3WalletEvent;

/**
 * @author shijie
 */
public class Web3WalletBillEventDTO {

    private Long id;

    private String transaction;

    private String displayType;

    private String productCode;

    private String productId;

    private String productName;

    private String fromAddress;

    private Long createdAt;

    private String currency;

    private String value;

    private String incomeExpense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
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

    public String getIncomeExpense() {
        return incomeExpense;
    }

    public void setIncomeExpense(String incomeExpense) {
        this.incomeExpense = incomeExpense;
    }

    public static Web3WalletBillEventDTO from(Web3WalletEvent entity) {
        Web3WalletBillEventDTO eventDTO = new Web3WalletBillEventDTO();
        eventDTO.setId(entity.getId());
        eventDTO.setProductCode(eventDTO.getProductCode());
        eventDTO.setProductId(eventDTO.getProductId());
        eventDTO.setCurrency(eventDTO.getCurrency());
        eventDTO.setValue(entity.getValue());
        eventDTO.setCreatedAt(entity.getCreatedAt());
        eventDTO.setFromAddress(entity.getFromAddress());
        eventDTO.setTransaction(entity.getTransaction());
        return eventDTO;
    }
}