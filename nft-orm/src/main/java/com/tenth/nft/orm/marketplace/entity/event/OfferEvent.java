package com.tenth.nft.orm.marketplace.entity.event;

import com.tenth.nft.orm.marketplace.entity.NftActivityEvent;

/**
 * @author shijie
 */
public class OfferEvent implements NftActivityEvent {

    private Long from;
    private Integer quantity;
    private Float price;
    private String currency;
    private Boolean cancel;
    private String reason;

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getFrom() {
        return from;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
