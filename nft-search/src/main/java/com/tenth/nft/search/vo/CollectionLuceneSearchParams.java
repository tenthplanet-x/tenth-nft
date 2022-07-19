package com.tenth.nft.search.vo;

import com.tpulse.commons.biz.dto.PageRequest;

/**
 * @author shijie
 */
public class CollectionLuceneSearchParams extends PageRequest {

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

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder{

        CollectionLuceneSearchParams params = new CollectionLuceneSearchParams();

        public Builder uid(Long uid) {
            params.uid = uid;
            return this;
        }

        public Builder categoryId(Long categoryId) {
            params.categoryId = categoryId;
            return this;
        }

        public CollectionLuceneSearchParams build() {
            return params;
        }

        public Builder page(int page) {
            params.setPage(page);
            return this;
        }

        public Builder pageSize(int pageSize) {
            params.setPageSize(pageSize);
            return this;
        }
    }
}
