package com.tenth.nft.web3.dto;

/**
 * @author shijie
 */
public class Web3WalletBalance {

    private String wallet;

    private String currency;

    private String balance;

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

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }
}
