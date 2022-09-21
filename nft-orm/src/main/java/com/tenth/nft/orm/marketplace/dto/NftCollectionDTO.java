package com.tenth.nft.orm.marketplace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.orm.FloatToStringDecoder;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.orm.marketplace.entity.NftCollection;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class NftCollectionDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @JsonIgnore
    @SimpleField
    private Long uid;

    @SimpleField
    private String name;

    @SimpleField
    private String desc;

    @SimpleField
    private String logoImage;

    @SimpleField
    private String featuredImage;

    @SimpleField
    private Long category;

    private String creatorFeeRate;

    @SimpleField
    private String blockchain;

    @SimpleField
    private Integer items;

    private UserProfileDTO creatorProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getCreatorFeeRate() {
        return creatorFeeRate;
    }

    public void setCreatorFeeRate(String creatorFeeRate) {
        this.creatorFeeRate = creatorFeeRate;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public Integer getItems() {
        return items;
    }

    public void setItems(Integer items) {
        this.items = items;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public UserProfileDTO getCreatorProfile() {
        return creatorProfile;
    }

    public void setCreatorProfile(UserProfileDTO creatorProfile) {
        this.creatorProfile = creatorProfile;
    }

    public static NftMarketplace.CollectionDTO toProto(NftCollection nftCollection) {

        NftMarketplace.CollectionDTO.Builder builder = NftMarketplace.CollectionDTO.newBuilder();
        builder.setId(nftCollection.getId());
        builder.setCreator(String.valueOf(nftCollection.getUid()));
        if(null != nftCollection.getCategory()){
            builder.setCategory(nftCollection.getCategory());
        }
        builder.setName(nftCollection.getName());
        if(!Strings.isNullOrEmpty(nftCollection.getDesc())){
            builder.setDesc(nftCollection.getDesc());
        }
        builder.setLogoImage(nftCollection.getLogoImage());
        if(!Strings.isNullOrEmpty(nftCollection.getFeaturedImage())){
            builder.setFeaturedImage(nftCollection.getFeaturedImage());
        }
        builder.setCreatedAt(nftCollection.getCreatedAt());
        builder.setBlockchain(nftCollection.getBlockchain());
        if(!Strings.isNullOrEmpty(nftCollection.getCreatorFeeRate())){
            builder.setCreatorFeeRate(nftCollection.getCreatorFeeRate());
        }
        builder.setItems(nftCollection.getItems());

        return builder.build();
    }

    public static NftCollectionDTO from(NftMarketplace.CollectionDTO collectionDTO) {
        return from(collectionDTO, NftCollectionDTO.class);
    }

    public static <T extends NftCollectionDTO> T from(NftMarketplace.CollectionDTO collectionDTO, Class<T> reflectionDTO) {

        T nftCollectionDTO = null;
        try {
            nftCollectionDTO = reflectionDTO.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            throw new RuntimeException("", e);
        }

        nftCollectionDTO.setId(collectionDTO.getId());
        nftCollectionDTO.setUid(Long.valueOf(collectionDTO.getCreator()));
        if(collectionDTO.hasCategory()){
            nftCollectionDTO.setCategory(collectionDTO.getCategory());
        }
        nftCollectionDTO.setName(collectionDTO.getName());
        nftCollectionDTO.setDesc(Strings.emptyToNull(collectionDTO.getDesc()));
        nftCollectionDTO.setLogoImage(Strings.emptyToNull(collectionDTO.getLogoImage()));
        nftCollectionDTO.setFeaturedImage(Strings.emptyToNull(collectionDTO.getFeaturedImage()));
        nftCollectionDTO.setBlockchain(collectionDTO.getBlockchain());
        nftCollectionDTO.setCreatorFeeRate(collectionDTO.getCreatorFeeRate());
        nftCollectionDTO.setItems(collectionDTO.getItems());

        return nftCollectionDTO;
    }
}
