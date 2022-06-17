package com.tenth.nft.search.dto;

import com.tpulse.commons.biz.dto.PageRequest;

/**
 * @author shijie
 */
public class SearchCollectionListRequest extends PageRequest {

    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
