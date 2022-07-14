package com.tenth.nft.search.lucenedao;

import com.tpulse.gs.lucenedb.defination.annotation.LuceneDocument;
import com.tpulse.gs.lucenedb.defination.annotation.LuceneDocumentField;
import com.tpulse.gs.lucenedb.defination.annotation.LuceneDocumentId;

import java.util.List;

/**
 * @author shijie
 */
@LuceneDocument("nft_assets")
public class NftAssetsLuceneDTO {

    @LuceneDocumentId
    @LuceneDocumentField
    private Long id;
    @LuceneDocumentField
    private Long collectionId;
    @LuceneDocumentField(sortable = true)
    private Long createdAt;
    @LuceneDocumentField
    private List<Long> owners;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public List<Long> getOwners() {
        return owners;
    }

    public void setOwners(List<Long> owners) {
        this.owners = owners;
    }
}
