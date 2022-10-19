package com.tenth.nft.web3.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author shijie
 */
@Valid
public class Web3ContractApprovalConfirmRequest {

    @NotEmpty
    private String txn;

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }
}
