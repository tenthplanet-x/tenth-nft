package com.tenth.nft.search.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @author shijie
 */
@Valid
public class CollectionListSearchRequest extends PageRequest {

    @NotNull
    private Long uid;

    private Long categoryId;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
