package com.tenth.nft.exchange.common.wallet;

/**
 * @author shijie
 */
public class WalletBalance {

    private String blockchain;

    private String currency;

    private String balance;

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
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
