package com.tenth.nft.crawler.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Valid
public class ExternalNftItemEditRequest {

    @NotNull
    private Long id;

    private String name;

    private String previewUrl;

    private String thumbnailUrl;

    private String rawUrl;

    private String contactAddress;

    private String tokenId;

    public Long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPreviewUrl(){
        return previewUrl;
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    public String getRawUrl(){
        return rawUrl;
    }

    public String getContactAddress(){
        return contactAddress;
    }

    public String getTokenId(){
        return tokenId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPreviewUrl(String previewUrl){
        this.previewUrl = previewUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl){
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setRawUrl(String rawUrl){
        this.rawUrl = rawUrl;
    }

    public void setContactAddress(String contactAddress){
        this.contactAddress = contactAddress;
    }

    public void setTokenId(String tokenId){
        this.tokenId = tokenId;
    }
}
