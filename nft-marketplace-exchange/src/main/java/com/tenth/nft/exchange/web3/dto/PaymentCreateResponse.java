package com.tenth.nft.exchange.web3.dto;

/**
 * @author shijie
 */
public class PaymentCreateResponse {

    private String token;

    private String txnTo;

    private String txnData;

    private String txnValue;

    public PaymentCreateResponse() {

    }

    public PaymentCreateResponse(String token, String txnData, String txnValue, String txnTo) {
        this.token = token;
        this.txnData = txnData;
        this.txnValue = txnValue;
        this.txnTo = txnTo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTxnTo() {
        return txnTo;
    }

    public void setTxnTo(String txnTo) {
        this.txnTo = txnTo;
    }

    public String getTxnData() {
        return txnData;
    }

    public void setTxnData(String txnData) {
        this.txnData = txnData;
    }

    public String getTxnValue() {
        return txnValue;
    }

    public void setTxnValue(String txnValue) {
        this.txnValue = txnValue;
    }
}
