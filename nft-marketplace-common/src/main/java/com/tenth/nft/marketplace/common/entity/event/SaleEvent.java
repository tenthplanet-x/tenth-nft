package com.tenth.nft.marketplace.common.entity.event;

import com.tenth.nft.marketplace.common.entity.NftActivityEvent;

/**
 * @author shijie
 */
public class SaleEvent implements NftActivityEvent {

    private String from;

    private String to;

    private Integer quantity;

    private String price;

    private String currency;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
