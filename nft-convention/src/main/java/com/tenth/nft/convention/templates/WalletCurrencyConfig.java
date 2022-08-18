package com.tenth.nft.convention.templates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * @author shijie
 */
@JsonDeserialize(builder = WalletCurrencyConfig.Builder.class)
public class WalletCurrencyConfig {

    private Integer id;

    private String blockchain;

    private String code;

    private String label;

    private Boolean enable;

    private Boolean main;

    private Boolean exchange;

    private Boolean bid;

    private String exchangeRate;

    private String icon;

    private String backgroundColor;

    private String min;

    private String max;

    public Integer getId() {
        return id;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public Boolean getEnable() {
        return enable;
    }

    public Boolean getMain() {
        return main;
    }

    public Boolean getExchange() {
        return exchange;
    }

    public Boolean getBid() {
        return bid;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public String getIcon() {
        return icon;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder{

        private WalletCurrencyConfig config = new WalletCurrencyConfig();

        public void setId(Integer id) {
            config.id = id;
        }

        public void setBlockchain(String blockchain) {
            config.blockchain = blockchain;
        }

        public void setCode(String code) {
            config.code = code;
        }

        public void setLabel(String label) {
            config.label = label;
        }

        public void setEnable(Boolean enable) {
            config.enable = enable;
        }

        public void setMain(Boolean main) {
            config.main = main;
        }

        public void setExchange(Boolean exchange) {
            config.exchange = exchange;
        }

        public void setBid(Boolean bid) {
            config.bid = bid;
        }

        public void setExchangeRate(String exchangeRate) {
            config.exchangeRate = exchangeRate;
        }

        public void setIcon(String icon) {
            config.icon = icon;
        }

        public void setBackgroundColor(String backgroundColor) {
            config.backgroundColor = backgroundColor;
        }

        public void setMin(String min) {
            config.min = min;
        }

        public void setMax(String max) {
            config.max = max;
        }

        public WalletCurrencyConfig build(){
            return config;
        }

    }
}
