package com.tenth.nft.crawler.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
@Valid
public class NftCollectionListRequest extends PageRequest {

    private Long categoryId;

    private String name;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
