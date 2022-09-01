package com.tenth.nft.web3.dto;

/**
 * @author shijie
 */
public class Web3WalletBalance {

    private String currency;

    private String balance;

    public Web3WalletBalance(String currency, String balance) {
        this.currency = currency;
        this.balance = balance;
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
