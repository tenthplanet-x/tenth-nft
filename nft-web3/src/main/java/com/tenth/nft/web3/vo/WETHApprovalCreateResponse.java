package com.tenth.nft.web3.vo;

/**
 * @author shijie
 */
public class WETHApprovalCreateResponse {

    private String from;

    private String txnTo;

    private String txnData;

    public WETHApprovalCreateResponse(String from, String txnTo, String txnValue) {
        this.from = from;
        this.txnTo = txnTo;
        this.txnData = txnValue;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
}
