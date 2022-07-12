package com.tenth.nft.orm.marketplace.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author shijie
 */
public class NftBelongIdDTO implements SimpleResponse {

    @SimpleField(name = "owner")
    private Long owner;

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
