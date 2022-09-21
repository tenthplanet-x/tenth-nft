package com.tenth.nft.marketplace.common.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author shijie
 */
public class NftListingDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    private Long id;
    @SimpleField
    private Long assetsId;
    @SimpleField
    private String currency;
    @SimpleField
    private String price;
    @SimpleField
    private Integer quantity;
    @SimpleField
    private String seller;
    @SimpleField
    private Long startAt;
    @SimpleField
    private Long expireAt;
    @SimpleField
    private Long createdAt;

    //private UserProfileDTO sellerProfile;

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    //    public UserProfileDTO getSellerProfile() {
//        return sellerProfile;
//    }
//
//    public void setSellerProfile(UserProfileDTO sellerProfile) {
//        this.sellerProfile = sellerProfile;
//    }

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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public static NftListingDTO from(NftExchange.NftListingDTO result) {
//
//        NftListingDTO dto = new NftListingDTO();
//        dto.setId(result.getId());
//        dto.setAssetsId(result.getAssetsId());
//        dto.setCurrency(result.getCurrency());
//        dto.setPrice(result.getPrice());
//        dto.setQuantity(result.getQuantity());
//        dto.setSeller(result.getSeller());
//        dto.setStartAt(result.getStartAt());
//        dto.setExpireAt(result.getExpireAt());
//        dto.setCreatedAt(result.getCreatedAt());
//        return dto;
//    }
//
//    public static NftExchange.NftListingDTO toProto(NftListing listing) {
//
//        return NftExchange.NftListingDTO.newBuilder()
//                .setId(listing.getId())
//                .setAssetsId(listing.getAssetsId())
//                .setSeller(listing.getUid())
//                .setPrice(listing.getPrice())
//                .setCurrency(listing.getCurrency())
//                .setQuantity(listing.getQuantity())
//                .setStartAt(listing.getStartAt())
//                .setExpireAt(listing.getExpireAt())
//                .setCreatedAt(listing.getCreatedAt())
//                .build();
//    }


}
