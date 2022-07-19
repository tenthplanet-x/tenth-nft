package com.tenth.nft.search.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


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
