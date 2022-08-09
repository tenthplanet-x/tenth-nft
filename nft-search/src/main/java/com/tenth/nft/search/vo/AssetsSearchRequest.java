package com.tenth.nft.search.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * @author shijie
 */
@Valid
public class AssetsSearchRequest extends PageRequest {

    @NotNull
    private Long collectionId;

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    @Override
    public int getPageSize() {
        return 10;
    }
}
