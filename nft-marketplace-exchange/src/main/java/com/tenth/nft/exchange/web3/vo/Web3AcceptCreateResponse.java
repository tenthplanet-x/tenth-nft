package com.tenth.nft.exchange.web3.vo;

/**
 * @author shijie
 */
public class Web3AcceptCreateResponse {

    private String from;

    private String txnValue;

    private String txnTo;

    private String txnData;

    private String nonce;

    private String content;

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

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
