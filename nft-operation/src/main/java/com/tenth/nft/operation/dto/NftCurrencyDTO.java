package com.tenth.nft.operation.dto;

import com.ruixi.tpulse.convention.orm.FloatToStringDecoder;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
public class NftCurrencyDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private String blockchain;

    @SimpleField
    private String code;

    @SimpleField
    private String label;

    @SimpleField
    private Boolean enable;

    @SimpleField
    private Integer order;

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

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setBlockchain(String blockchain){
        this.blockchain = blockchain;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public void setEnable(Boolean enable){
        this.enable = enable;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
}
