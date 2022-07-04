package com.tenth.nft.marketplace.dto;

import com.tenth.nft.marketplace.entity.NftAssetsType;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class NftAssetsDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private NftAssetsType type;

    @SimpleField
    private Long collectionId;

    @SimpleField
    private String url;

    @SimpleField
    private String previewUrl;

    @SimpleField
    private String name;

    @SimpleField
    private String desc;

    @SimpleField
    private Integer supply;

    @SimpleField
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
