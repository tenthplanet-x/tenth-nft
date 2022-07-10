package com.tenth.nft.orm.marketplace.entity.event;

import com.tenth.nft.orm.marketplace.entity.NftActivityEvent;

/**
 * @author shijie
 */
public class MintEvent implements NftActivityEvent {

    private Long to;

    private Integer quantity;

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
