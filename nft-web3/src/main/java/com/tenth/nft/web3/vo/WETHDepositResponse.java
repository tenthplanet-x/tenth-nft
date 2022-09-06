package com.tenth.nft.web3.vo;

/**
 * @author shijie
 */
public class WETHDepositResponse {

    private String from;

    private String txnValue;

    private String txnTo;

    private String txnData;

    private String content;

    public WETHDepositResponse() {
    }

    public WETHDepositResponse(String txnFrom, String txnValue, String txnTo, String txnData, String content) {
        this.from = txnFrom;
        this.txnValue = txnValue;
        this.txnTo = txnTo;
        this.txnData = txnData;
        this.content = content;
    }

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
}
