package com.tenth.nft.marketplace.common.entity.event;

import com.tenth.nft.marketplace.common.entity.NftActivityEvent;

/**
 * @author shijie
 */
public class MintEvent implements NftActivityEvent {

    private String to;

    private Integer quantity;

    private String from;

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
