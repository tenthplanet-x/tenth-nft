package com.tenth.nft.web3.dto;

/**
 * @author shijie
 */
public class Web3WalletBalance {

    private String currency;

    private String balance;

    private String icon;

    private String label;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Web3WalletBalance(String currency, String balance, String label, String icon) {
        this.currency = currency;
        this.balance = balance;
        this.label = label;
        this.icon = icon;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }



}
