package com.tenth.nft.operation.vo;

import javax.validation.Valid;

/**
 * @author shijie
 */
@Valid
public class CurrenySearchRequest {

    private String blockchain;

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }
}
