package com.tenth.nft.web3.entity;


/**
 * @author shijie
 */
public class Web3WalletBillProfit {

    private String to;

    private Integer activityCfgId;

    private String currency;

    private String value;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getActivityCfgId() {
        return activityCfgId;
    }

    public void setActivityCfgId(Integer activityCfgId) {
        this.activityCfgId = activityCfgId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
