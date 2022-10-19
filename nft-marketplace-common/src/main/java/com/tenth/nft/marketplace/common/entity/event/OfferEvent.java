package com.tenth.nft.marketplace.common.entity.event;

import com.tenth.nft.marketplace.common.entity.NftActivityEvent;

/**
 * @author shijie
 */
public class OfferEvent implements NftActivityEvent {

    private String from;
    private Integer quantity;
    private String price;
    private String currency;
    private Boolean cancel;
    private String reason;
    private Long expireAt;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
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

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }
}
