package com.tenth.nft.web3.vo;

/**
 * @author shijie
 */
public class WETHTxnCheckRequest {

    private String content;

    private String txn;

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
