package com.tenth.nft.marketplace.web3.dto;

/**
 * @author shijie
 */
public class Web3SendTransactionTicket {

    private String content;

    private String txnTo;

    private String txnData;

    private String txnValue;

    private String from;

    private String walletBridgeUrl;

    public Web3SendTransactionTicket() {

    }

    public Web3SendTransactionTicket(String token, String txnData, String txnValue, String txnTo, String from) {
        this.content = token;
        this.txnData = txnData;
        this.txnValue = txnValue;
        this.txnTo = txnTo;
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setWalletBridgeUrl(String walletBridgeUrl) {
        this.walletBridgeUrl = walletBridgeUrl;
    }

    public String getWalletBridgeUrl() {
        return walletBridgeUrl;
    }

}
