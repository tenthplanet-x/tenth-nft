package com.tenth.nft.crawler.sdk.opensea.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shijie
 * @createdAt 2022/6/14 15:50
 */
public class OpenseaItemDTO {

    private boolean success = true;

    private Long id;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("image_preview_url")
    private String imagePreviewUrl;

    @JsonProperty("image_thumbnail_url")
    private String imageThumbnailUrl;

    @JsonProperty("image_original_url")
    private String imageOriginalUrl;

    private String name;

    @JsonProperty("token_metadata")
    private String tokenMetadata;
    private String tokenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePreviewUrl() {
        return imagePreviewUrl;
    }

    public void setImagePreviewUrl(String imagePreviewUrl) {
        this.imagePreviewUrl = imagePreviewUrl;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    public String getImageOriginalUrl() {
        return imageOriginalUrl;
    }

    public void setImageOriginalUrl(String imageOriginalUrl) {
        this.imageOriginalUrl = imageOriginalUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTokenMetadata() {
        return tokenMetadata;
    }

    public void setTokenMetadata(String tokenMetadata) {
        this.tokenMetadata = tokenMetadata;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
