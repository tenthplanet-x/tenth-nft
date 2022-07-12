package com.tenth.nft.search.dto;

import com.tenth.nft.orm.marketplace.entity.NftAssetsType;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author shijie
 */
public class AssetsOwnSearchDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private NftAssetsType type;

    @SimpleField
    private String url;

    @SimpleField
    private String previewUrl;

    private AssetsDetailSearchDTO.ListingDTO currentListing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NftAssetsType getType() {
        return type;
    }

    public void setType(NftAssetsType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public AssetsDetailSearchDTO.ListingDTO getCurrentListing() {
        return currentListing;
    }

    public void setCurrentListing(AssetsDetailSearchDTO.ListingDTO currentListing) {
        this.currentListing = currentListing;
    }
}
