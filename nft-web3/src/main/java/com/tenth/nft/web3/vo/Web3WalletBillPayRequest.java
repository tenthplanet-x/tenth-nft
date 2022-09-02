package com.tenth.nft.web3.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author shijie
 */
@Valid
public class Web3WalletBillPayRequest {

    @NotEmpty
    private String content;
    @NotEmpty
    private String txn;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

}
