package com.tenth.nft.marketplace.common.dto;

import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftCollection;
import com.tenth.nft.marketplace.common.entity.NftAssetsType;
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

    @SimpleField
    private String contractAddress;

    @SimpleField
    private String tokenStandard;

    @SimpleField
    private String token;

    private ListingDTO currentListing;

    @SimpleField
    private String creator;

    private NftUserProfileDTO creatorProfile;

    private String unionId;

    private String collectionUnionId;
    private String collectionName;

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

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getTokenStandard() {
        return tokenStandard;
    }

    public void setTokenStandard(String tokenStandard) {
        this.tokenStandard = tokenStandard;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ListingDTO getCurrentListing() {
        return currentListing;
    }

    public void setCurrentListing(ListingDTO currentListing) {
        this.currentListing = currentListing;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public NftUserProfileDTO getCreatorProfile() {
        return creatorProfile;
    }

    public void setCreatorProfile(NftUserProfileDTO creatorProfile) {
        this.creatorProfile = creatorProfile;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getCollectionUnionId() {
        return collectionUnionId;
    }

    public void setCollectionUnionId(String collectionUnionId) {
        this.collectionUnionId = collectionUnionId;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public static class ListingDTO{

        private Long id;

        private String currency;

        private String price;

        private Integer quantity;

        private Long startAt;

        private Long expireAt;

        private NftUserProfileDTO sellerProfile;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Long getStartAt() {
            return startAt;
        }

        public void setStartAt(Long startAt) {
            this.startAt = startAt;
        }

        public Long getExpireAt() {
            return expireAt;
        }

        public void setExpireAt(Long expireAt) {
            this.expireAt = expireAt;
        }

        public NftUserProfileDTO getSellerProfile() {
            return sellerProfile;
        }

        public void setSellerProfile(NftUserProfileDTO sellerProfile) {
            this.sellerProfile = sellerProfile;
        }

        //        public static ListingDTO from(NftExchange.NftListingDTO listing) {
//            ListingDTO dto = new ListingDTO();
//            dto.setId(listing.getId());
//            dto.setCurrency(listing.getCurrency());
//            dto.setExpireAt(listing.getExpireAt());
//            dto.setPrice(listing.getPrice());
//            dto.setQuantity(listing.getQuantity());
//            dto.setStartAt(listing.getStartAt());
//            return dto;
//        }
    }

//    public static NftMarketplace.AssetsDTO toProto(NftAssets nftAssets) {
//        NftMarketplace.AssetsDTO.Builder builder = NftMarketplace.AssetsDTO.newBuilder();
//        builder.setId(nftAssets.getId());
//        if(null != nftAssets.getType()){
//            builder.setType(nftAssets.getType().name());
//        }
//        builder.setCollectionId(nftAssets.getCollectionId());
//        builder.setUrl(nftAssets.getUrl());
//        if(!Strings.isNullOrEmpty(nftAssets.getPreviewUrl())){
//            builder.setPreviewUrl(nftAssets.getPreviewUrl());
//        }
//        builder.setName(nftAssets.getName());
//        if(!Strings.isNullOrEmpty(nftAssets.getDesc())){
//            builder.setDesc(nftAssets.getDesc());
//        }
//        builder.setSupply(nftAssets.getSupply());
//        builder.setCreatedAt(nftAssets.getCreatedAt());
//        builder.setBlockchain(nftAssets.getBlockchain());
//        builder.setCreator(String.valueOf(nftAssets.getCreator()));
//        if(!Strings.isNullOrEmpty(nftAssets.getCreatorFeeRate())){
//            builder.setCreatorFeeRate(nftAssets.getCreatorFeeRate());
//        }
//        if(!Strings.isNullOrEmpty(nftAssets.getCreatorAddress())){
//            builder.setCreatorAddress(nftAssets.getCreatorAddress());
//        }
//        if(!Strings.isNullOrEmpty(nftAssets.getSignature())){
//            builder.setSignature(nftAssets.getSignature());
//        }
//
//        if(null != nftAssets.getContractAddress()){
//            builder.setContractAddress(nftAssets.getContractAddress());
//            builder.setTokenStandard(nftAssets.getTokenStandard());
//            builder.setToken(nftAssets.getToken());
//        }
//
//        if(TokenMintStatus.SUCCESS.equals(nftAssets.getMintStatus())){
//            builder.setMint(true);
//        }
//
//        return builder.build();
//    }
//
//    public static NftAssetsDTO from(NftMarketplace.AssetsDTO proto) {
//
//        NftAssetsDTO nftAssetsDTO = new NftAssetsDTO();
//        nftAssetsDTO.setId(proto.getId());
//        if(proto.hasType()){
//            nftAssetsDTO.setType(NftAssetsType.valueOf(proto.getType()));
//        }
//        nftAssetsDTO.setCollectionId(proto.getCollectionId());
//        nftAssetsDTO.setUrl(proto.getUrl());
//        nftAssetsDTO.setPreviewUrl(Strings.emptyToNull(proto.getPreviewUrl()));
//        nftAssetsDTO.setName(proto.getName());
//        nftAssetsDTO.setDesc(Strings.emptyToNull(proto.getDesc()));
//        nftAssetsDTO.setSupply(proto.getSupply());
//
//        nftAssetsDTO.setBlockchain(Strings.emptyToNull(proto.getBlockchain()));
//        nftAssetsDTO.setContractAddress(Strings.emptyToNull(proto.getContractAddress()));
//        nftAssetsDTO.setTokenStandard(Strings.emptyToNull(proto.getTokenStandard()));
//        nftAssetsDTO.setToken(Strings.emptyToNull(proto.getToken()));
//
//
//
//        return nftAssetsDTO;
//    }
}
