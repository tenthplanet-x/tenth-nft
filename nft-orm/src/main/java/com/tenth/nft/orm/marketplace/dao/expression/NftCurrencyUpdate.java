package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
public class NftCurrencyUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private String code;

    @SimpleWriteParam
    private String label;

    @SimpleWriteParam
    private Boolean enable;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam
    private Integer order;
    @SimpleWriteParam
    private Float exchangeRate;
    @SimpleWriteParam
    private Boolean main;
    @SimpleWriteParam
    private Boolean bid;
    @SimpleWriteParam
    private Boolean exchange;
    @SimpleWriteParam
    private String icon;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public String getCode(){
        return code;
    }

    public String getLabel(){
        return label;
    }

    public Boolean getEnable(){
        return enable;
    }

    public Integer getOrder() {
        return order;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public Boolean getMain() {
        return main;
    }

    public Boolean getBid() {
        return bid;
    }

    public Boolean getExchange() {
        return exchange;
    }

    public String getIcon() {
        return icon;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftCurrencyUpdate update = new NftCurrencyUpdate();

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public Builder setCode(String code){
            update.code = code;
            return this;
        }

        public Builder setLabel(String label){
            update.label = label;
            return this;
        }

        public Builder setEnable(Boolean enable){
            update.enable = enable;
            return this;
        }

        public NftCurrencyUpdate build(){
            return update;
        }

        public Builder order(Integer order) {
            update.order = order;
            return this;
        }

        public Builder setIcon(String icon) {
            update.icon = icon;
            return this;
        }

        public Builder setExchange(Boolean exchange) {
            update.exchange = exchange;
            return this;
        }

        public Builder setBid(Boolean bid) {
            update.bid = bid;
            return this;
        }

        public Builder setMain(Boolean main) {
            update.main = main;
            return this;
        }

        public Builder setExchangeRate(Float exchangeRate) {
            update.exchangeRate = exchangeRate;
            return this;
        }
    }

}
