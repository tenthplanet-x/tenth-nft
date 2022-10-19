package com.tenth.nft.marketplace.stats.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;


/**
 * @author shijie
 */
@Valid
public class CollectionRecommentListSearchRequest extends PageRequest {

    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
