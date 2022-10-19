package com.tenth.nft.marketplace.common.dto;

/**
 * @author shijie
 */
public class NftWalletPayTicket {

    private String content;
    private String currency;
    private String value;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
