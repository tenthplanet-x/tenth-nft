package com.tenth.nft.operation.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Valid
public class NftCurrencyEditRequest {

    @NotNull
    private Long id;

    private String blockchain;

    private String code;

    private String label;

    private Boolean enable;

    private Integer order;

    private Boolean main;

    private Boolean exchange;

    private Boolean bid;

    private Float exchangeRate;

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

    public Boolean getExchange() {
        return exchange;
    }

    public void setExchange(Boolean exchange) {
        this.exchange = exchange;
    }

    public Boolean getBid() {
        return bid;
    }

    public void setBid(Boolean bid) {
        this.bid = bid;
    }

    public Float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
