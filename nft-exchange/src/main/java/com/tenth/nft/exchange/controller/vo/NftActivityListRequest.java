package com.tenth.nft.exchange.controller.vo;

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

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }
}
