package com.tenth.nft.marketplace.vo;

import com.tenth.nft.marketplace.entity.NftAssetsType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@Valid
public class NftAssetsEditRequest {

    @NotNull
    private Long id;

    private NftAssetsType type;

    private Long collectionId;

    private String url;

    private String previewUrl;

    private String name;

    private String desc;

    private Integer supply;

    private String blockchain;

    public Long getId() {
        return id;
    }

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

    public void setId(Long id) {
        this.id = id;
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
