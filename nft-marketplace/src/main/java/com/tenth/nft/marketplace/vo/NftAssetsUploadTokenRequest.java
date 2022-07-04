package com.tenth.nft.marketplace.vo;

import com.tenth.nft.marketplace.entity.NftAssetsType;

import javax.validation.Valid;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 10:06
 */
@Valid
public class NftAssetsUploadTokenRequest {

    private NftAssetsType type;

    private String url;

    private String previewUrl;

    private String name;

    private String desc;

    private Integer supply = 1;

    public NftAssetsType getType(){
        return type;
    }

    public String getUrl(){
        return url;
    }

    public String getPreviewUrl(){
        return previewUrl;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public Integer getSupply(){
        return supply;
    }

    public void setType(NftAssetsType type){
        this.type = type;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setPreviewUrl(String previewUrl){
        this.previewUrl = previewUrl;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setSupply(Integer supply){
        this.supply = supply;
    }

}
