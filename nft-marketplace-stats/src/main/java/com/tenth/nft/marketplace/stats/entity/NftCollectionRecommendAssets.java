package com.tenth.nft.marketplace.stats.entity;

import com.tenth.nft.marketplace.common.entity.NftAssetsType;

/**
 * @author shijie
 */
public class NftCollectionRecommendAssets {

    public Long id;

    private NftAssetsType type;

    private String previewUrl;

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

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
