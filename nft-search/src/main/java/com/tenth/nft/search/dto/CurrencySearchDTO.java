package com.tenth.nft.search.dto;

import com.tenth.nft.orm.marketplace.decoder.PricesToStringDecoder;
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
    @SimpleField
    private Boolean main;
    @SimpleField
    private Boolean bid;
    @SimpleField
    private Boolean exchange;
    @SimpleField(decoder = FloatToStringDecoder.class)
    private String exchangeRate;
    @SimpleField
    private String icon;
    @SimpleField
    private String min;
    @SimpleField
    private String max;

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

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }

    public Boolean getBid() {
        return bid;
    }

    public void setBid(Boolean bid) {
        this.bid = bid;
    }

    public Boolean getExchange() {
        return exchange;
    }

    public void setExchange(Boolean exchange) {
        this.exchange = exchange;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

}
