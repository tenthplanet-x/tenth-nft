package com.tenth.nft.marketplace.common.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class AbsNftCollectionUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private String name;

    @SimpleWriteParam
    private String desc;

    @SimpleWriteParam
    private String logoImage;

    @SimpleWriteParam
    private String featuredImage;

    @SimpleWriteParam
    private Long category;

    @SimpleWriteParam
    private String creatorFeeRate;

    @SimpleWriteParam
    private String blockchain;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private Long items;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getUid(){
        return uid;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public String getLogoImage(){
        return logoImage;
    }

    public String getFeaturedImage(){
        return featuredImage;
    }

    public Long getCategory(){
        return category;
    }

    public String getCreatorFeeRate() {
        return creatorFeeRate;
    }

    public String getBlockchain(){
        return blockchain;
    }

    public Long getItems() {
        return items;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private AbsNftCollectionUpdate update = new AbsNftCollectionUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setName(String name){
            update.name = name;
            return this;
        }

        public Builder setDesc(String desc){
            update.desc = desc;
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

        public Builder setCategory(Long category){
            update.category = category;
            return this;
        }

        public Builder setCreatorFeeRate(String creatorFee){
            update.creatorFeeRate = creatorFee;
            return this;
        }

        public Builder setBlockchain(String blockchain){
            update.blockchain = blockchain;
            return this;
        }

        public AbsNftCollectionUpdate build(){
            return update;
        }

        public Builder items(Long items) {
            update.items = items;
            return this;
        }
    }

}
