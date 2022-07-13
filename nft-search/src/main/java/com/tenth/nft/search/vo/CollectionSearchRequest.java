package com.tenth.nft.search.vo;

import com.tpulse.commons.biz.dto.PageRequest;


/**
 * @author shijie
 */
public class CollectionSearchRequest extends PageRequest {

    private Long categoryId;

    private Long uid;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
