package com.tenth.nft.marketplace.common.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author shijie
 */
@Valid
public class NftCollectionOwnListRequest extends PageRequest {

    @NotNull
    private Long owner;

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
