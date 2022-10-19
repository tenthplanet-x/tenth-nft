package com.tenth.nft.web3.vo;

/**
 * @author shijie
 */
public class Web3WalletPayRequest {

    private String token;

    private String txn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }
}
