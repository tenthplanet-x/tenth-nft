package com.tenth.nft.exchange.buildin.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.orm.marketplace.entity.NftOffer;
import com.tenth.nft.protobuf.NftExchange;

/**
 * @author shijie
 */
public class NftOfferDTO {

    private Long id;

    private Long assetsId;

    private Long uid;

    private Integer quantity;

    private String price;

    private String currency;

    private Long createdAt;

    private Long expireAt;

    private UserProfileDTO userProfile;

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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public static NftExchange.NftOfferDTO from(NftOffer nftOffer) {

        return NftExchange.NftOfferDTO.newBuilder()
                .setId(nftOffer.getId())
                .setUid(nftOffer.getUid())
                .setAssetsId(nftOffer.getAssetsId())
                .setQuantity(nftOffer.getQuantity())
                .setPrice(nftOffer.getPrice())
                .setCurrency(nftOffer.getCurrency())
                .setCreatedAt(nftOffer.getCreatedAt())
                .setExpireAt(nftOffer.getExpireAt())
                .build();
    }

    public static NftOfferDTO from(NftExchange.NftOfferDTO nftOffer) {

        NftOfferDTO dto = new NftOfferDTO();
        dto.setId(nftOffer.getId());
        dto.setUid(nftOffer.getUid());
        dto.setAssetsId(nftOffer.getAssetsId());
        dto.setQuantity(nftOffer.getQuantity());
        dto.setPrice(nftOffer.getPrice());
        dto.setCurrency(nftOffer.getCurrency());
        dto.setCreatedAt(nftOffer.getCreatedAt());
        dto.setExpireAt(nftOffer.getExpireAt());
        return dto;
    }

    public static NftOfferDTO convertFrom(NftOffer nftOffer) {

        NftOfferDTO dto = new NftOfferDTO();
        dto.setId(nftOffer.getId());
        dto.setUid(nftOffer.getUid());
        dto.setAssetsId(nftOffer.getAssetsId());
        dto.setQuantity(nftOffer.getQuantity());
        dto.setPrice(nftOffer.getPrice());
        dto.setCurrency(nftOffer.getCurrency());
        dto.setCreatedAt(nftOffer.getCreatedAt());
        dto.setExpireAt(nftOffer.getExpireAt());
        return dto;
    }
}
