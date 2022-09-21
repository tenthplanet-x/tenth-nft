package com.tenth.nft.marketplace.vo;

import com.tenth.nft.orm.marketplace.entity.NftAssetsType;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@Valid
public class NftAssetsCreateRequest {

    @NotNull
    private NftAssetsType type;

    @NotNull
    private Long collectionId;

    @NotEmpty
    private String url;

    private String previewUrl;

    @NotEmpty
    private String name;

    private String desc;

    private Integer supply = 1;

    @NotEmpty
    private String blockchain;

    public NftAssetsType getType(){
        return type;
    }

    public Long getCollectionId(){
        return collectionId;
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

    public void setCollectionId(Long collectionId){
        this.collectionId = collectionId;
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

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

}
