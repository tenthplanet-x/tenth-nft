package com.tenth.nft.search.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @author shijie
 */
@Valid
public class AssetsOwnSearchRequest extends PageRequest {

    @NotNull
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
