package com.tenth.nft.marketplace.common.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author shijie
 */
public class NftOfferDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    private Long id;

    @SimpleField
    private Long assetsId;
    @SimpleField
    private String buyer;
    @SimpleField
    private Integer quantity;
    @SimpleField
    private String price;
    @SimpleField
    private String currency;
    @SimpleField
    private Long createdAt;
    @SimpleField
    private Long expireAt;

//    private UserProfileDTO userProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }


//    public UserProfileDTO getUserProfile() {
//        return userProfile;
//    }
//
//    public void setUserProfile(UserProfileDTO userProfile) {
//        this.userProfile = userProfile;
//    }
//
//    public static NftOfferDTO fromFromEntity(NftOffer nftOffer) {
//
//        NftOfferDTO dto = new NftOfferDTO();
//        dto.setId(nftOffer.getId());
//        dto.setUid(nftOffer.getUid());
//        dto.setAssetsId(nftOffer.getAssetsId());
//        dto.setQuantity(nftOffer.getQuantity());
//        dto.setPrice(nftOffer.getPrice());
//        dto.setCurrency(nftOffer.getCurrency());
//        dto.setCreatedAt(nftOffer.getCreatedAt());
//        dto.setExpireAt(nftOffer.getExpireAt());
//
//        return dto;
//    }
//
//    public static NftExchange.NftOfferDTO from(NftOffer nftOffer) {
//
//        return NftExchange.NftOfferDTO.newBuilder()
//                .setId(nftOffer.getId())
//                .setUid(nftOffer.getUid())
//                .setAssetsId(nftOffer.getAssetsId())
//                .setQuantity(nftOffer.getQuantity())
//                .setPrice(nftOffer.getPrice())
//                .setCurrency(nftOffer.getCurrency())
//                .setCreatedAt(nftOffer.getCreatedAt())
//                .setExpireAt(nftOffer.getExpireAt())
//                .build();
//    }
//
//    public static NftOfferDTO convertFrom(NftOffer nftOffer) {
//
//        NftOfferDTO dto = new NftOfferDTO();
//        dto.setId(nftOffer.getId());
//        dto.setUid(nftOffer.getUid());
//        dto.setAssetsId(nftOffer.getAssetsId());
//        dto.setQuantity(nftOffer.getQuantity());
//        dto.setPrice(nftOffer.getPrice());
//        dto.setCurrency(nftOffer.getCurrency());
//        dto.setCreatedAt(nftOffer.getCreatedAt());
//        dto.setExpireAt(nftOffer.getExpireAt());
//        return dto;
//    }
}
