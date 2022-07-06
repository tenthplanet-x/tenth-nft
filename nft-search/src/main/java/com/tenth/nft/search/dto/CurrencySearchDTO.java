package com.tenth.nft.search.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author shijie
 */
public class CurrencySearchDTO implements SimpleResponse {

    @SimpleField
    private String blockchain;
    @SimpleField
    private String code;
    @SimpleField
    private String label;

    private String blockchainLabel;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public String getBlockchainLabel() {
        return blockchainLabel;
    }

    public void setBlockchainLabel(String blockchainLabel) {
        this.blockchainLabel = blockchainLabel;
    }
}
