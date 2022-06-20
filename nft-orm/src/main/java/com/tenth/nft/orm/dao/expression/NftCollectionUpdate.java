package com.tenth.nft.orm.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:22
 */
public class NftCollectionUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String name;

    @SimpleWriteParam
    private String desc;

    @SimpleWriteParam
    private String contractAddress;

    @SimpleWriteParam
    private String logoImage;

    @SimpleWriteParam
    private String featuredImage;

    @SimpleWriteParam
    private String bannerImage;

    @SimpleWriteParam
    private Float totalVolume;

    @SimpleWriteParam
    private Float floorPrice;

    @SimpleWriteParam
    private Integer totalSupply;

    @SimpleWriteParam
    private Long categoryId;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam
    private Long createdAt;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public String getContractAddress(){
        return contractAddress;
    }

    public String getLogoImage(){
        return logoImage;
    }

    public String getFeaturedImage(){
        return featuredImage;
    }

    public String getBannerImage(){
        return bannerImage;
    }

    public Float getTotalVolume(){
        return totalVolume;
    }

    public Float getFloorPrice(){
        return floorPrice;
    }

    public Integer getTotalSupply(){
        return totalSupply;
    }

    public Long getCategoryId(){
        return categoryId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftCollectionUpdate update = new NftCollectionUpdate();

        public Builder setName(String name){
            update.name = name;
            return this;
        }

        public Builder setDesc(String desc){
            update.desc = desc;
            return this;
        }

        public Builder setContractAddress(String contractAddress){
            update.contractAddress = contractAddress;
            return this;
        }

        public Builder setLogoImage(String logoImage){
            update.logoImage = logoImage;
            return this;
        }

        public Builder setFeaturedImage(String featuredImage){
            update.featuredImage = featuredImage;
            return this;
        }

        public Builder setBannerImage(String bannerImage){
            update.bannerImage = bannerImage;
            return this;
        }

        public Builder setTotalVolume(Float totalVolume){
            update.totalVolume = totalVolume;
            return this;
        }

        public Builder setFloorPrice(Float floorPrice){
            update.floorPrice = floorPrice;
            return this;
        }

        public Builder setTotalSupply(Integer totalSupply){
            update.totalSupply = totalSupply;
            return this;
        }

        public Builder setCategoryId(Long categoryId){
            update.categoryId = categoryId;
            return this;
        }

        public NftCollectionUpdate build(){
            return update;
        }

        public Builder setCreatedAtOnInsert(Long time) {
            update.createdAt = time;
            return this;
        }
    }

}
