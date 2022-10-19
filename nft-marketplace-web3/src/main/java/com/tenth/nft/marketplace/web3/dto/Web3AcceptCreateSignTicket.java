package com.tenth.nft.marketplace.web3.dto;

/**
 * @author shijie
 */
public class Web3AcceptCreateSignTicket {

    private String from;

    private String txnValue;

    private String txnTo;

    private String txnData;

    private String content;

    private String walletBridgeUrl;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTxnValue() {
        return txnValue;
    }

    public void setTxnValue(String txnValue) {
        this.txnValue = txnValue;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWalletBridgeUrl(String walletBridgeUrl) {
        this.walletBridgeUrl = walletBridgeUrl;
    }

    public String getWalletBridgeUrl() {
        return walletBridgeUrl;
    }
}
