package com.tenth.nft.search.lucenedao;

import com.tpulse.gs.lucenedb.defination.annotation.LuceneDocument;
import com.tpulse.gs.lucenedb.defination.annotation.LuceneDocumentField;
import com.tpulse.gs.lucenedb.defination.annotation.LuceneDocumentId;

/**
 * @author shijie
 */
@LuceneDocument("nft_collection")
public class NftCollectionLuceneDTO{

    @LuceneDocumentId
    @LuceneDocumentField
    private Long id;
    @LuceneDocumentField
    private Long categoryNull;
    @LuceneDocumentField
    private Long uid;
    @LuceneDocumentField(sortable = true)
    private Long createdAt;
    @LuceneDocumentField
    private Long category;
    @LuceneDocumentField(sortable = true)
    private Integer items;
    @LuceneDocumentField(sortable = true)
    private Double totalVolume;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryNull() {
        return categoryNull;
    }

    public void setCategoryNull(Long categoryNull) {
        this.categoryNull = categoryNull;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public Double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }
}
