package com.tenth.nft.web3.vo;

/**
 * @author shijie
 */
public class Web3ContractApprovalCreateResponse {

    private String txnFrom;
    private String txnTo;
    private String txnData;

    public String getTxnFrom() {
        return txnFrom;
    }

    public void setTxnFrom(String txnFrom) {
        this.txnFrom = txnFrom;
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
