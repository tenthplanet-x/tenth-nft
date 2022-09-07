package com.tenth.nft.web3.dto;

import com.tenth.nft.web3.entity.Web3WalletBill;

/**
 * @author shijie
 */
public class Web3WalletBillEventListDTO {

    private Long id;

    private String displayIcon;

    private String displayName;

    private Long createdAt;

    private String currency;

    private String value;

    private String incomeExpense;

    private String gasCurrency;

    private String gasUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public void setDisplayIcon(String displayIcon) {
        this.displayIcon = displayIcon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getGasCurrency() {
        return gasCurrency;
    }

    public void setGasCurrency(String gasCurrency) {
        this.gasCurrency = gasCurrency;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public static Web3WalletBillEventListDTO from(Web3WalletBill entity) {
        Web3WalletBillEventListDTO dto = new Web3WalletBillEventListDTO();
        dto.setId(entity.getId());
        dto.setCurrency(entity.getCurrency());
        dto.setValue(entity.getValue());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setGasCurrency(entity.getUsedGasValue());
        return dto;
    }
}
