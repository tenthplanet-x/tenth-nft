package com.tenth.nft.marketplace.common.vo;

import com.tenth.nft.marketplace.common.entity.NftActivityEventType;
import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author shijie
 */
@Valid
public class NftActivityListRequest extends PageRequest {

    @NotNull
    private Long assetsId;

    private NftActivityEventType event;

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public NftActivityEventType getEvent() {
        return event;
    }

    public void setEvent(NftActivityEventType event) {
        this.event = event;
    }
}
