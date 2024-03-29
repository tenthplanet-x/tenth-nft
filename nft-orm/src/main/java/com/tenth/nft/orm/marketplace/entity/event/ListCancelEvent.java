package com.tenth.nft.orm.marketplace.entity.event;

import com.tenth.nft.orm.marketplace.entity.NftActivityEvent;

/**
 * @author shijie
 */
public class ListCancelEvent implements NftActivityEvent {

    private Long from;

    private Long to;

    private Integer quantity;

    private String price;

    private String currency;

    private String reason;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
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

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
