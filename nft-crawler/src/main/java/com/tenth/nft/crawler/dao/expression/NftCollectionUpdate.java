package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
public class NftCollectionUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String name;

    @SimpleWriteParam
    private String logoImage;

    @SimpleWriteParam
    private String featuredImage;

    @SimpleWriteParam
    private String bannerImage;

    @SimpleWriteParam
    private Long createdAt;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private Float totalVolume;
    @SimpleWriteParam
    private Float floorPrice;
    @SimpleWriteParam
    private Integer totalSupply;
    @SimpleWriteParam
    private String desc;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getName(){
        return name;
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public Float getTotalVolume() {
        return totalVolume;
    }

    public Float getFloorPrice() {
        return floorPrice;
    }

    public Integer getTotalSupply() {
        return totalSupply;
    }

    public String getDesc() {
        return desc;
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

        public Builder setCreatedAtOnInsert(Long createdAt) {
            update.createdAt = createdAt;
            return this;
        }

        public NftCollectionUpdate build(){
            return update;
        }

        public Builder setVolume(Float totalVolume) {
            update.totalVolume = totalVolume;
            return this;
        }

        public Builder setFloorPrice(Float floorPrice) {
            update.floorPrice = floorPrice;
            return this;
        }

        public Builder setTotalSupply(Integer totalSupply) {
            update.totalSupply = totalSupply;
            return this;
        }

        public Builder setDesc(String desc) {
            update.desc = desc;
            return this;
        }

    }

}
