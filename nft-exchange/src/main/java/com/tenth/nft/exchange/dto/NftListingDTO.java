package com.tenth.nft.exchange.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.protobuf.NftExchange;

/**
 * @author shijie
 */
public class NftListingDTO {

    private Long id;

    private Long assetsId;

    private String currency;

    private String price;

    private Integer quantity;

    private Long seller;

    private UserProfileDTO sellerProfile;

    private Long startAt;

    private Long expireAt;

    private Long createdAt;

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

    public Long getSeller() {
        return seller;
    }

    public void setSeller(Long seller) {
        this.seller = seller;
    }

    public UserProfileDTO getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(UserProfileDTO sellerProfile) {
        this.sellerProfile = sellerProfile;
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

    public static NftListingDTO from(NftExchange.NftListingDTO result) {

        NftListingDTO dto = new NftListingDTO();
        dto.setId(result.getId());
        dto.setAssetsId(result.getAssetsId());
        dto.setCurrency(result.getCurrency());
        dto.setPrice(Prices.toString(result.getPrice()));
        dto.setQuantity(result.getQuantity());
        dto.setSeller(result.getSeller());
        dto.setStartAt(result.getStartAt());
        dto.setExpireAt(result.getExpireAt());
        dto.setCreatedAt(result.getCreatedAt());
        return dto;
    }

    public static NftExchange.NftListingDTO toProto(NftListing listing) {

        return NftExchange.NftListingDTO.newBuilder()
                .setId(listing.getId())
                .setAssetsId(listing.getAssetsId())
                .setSeller(listing.getUid())
                .setPrice(listing.getPrice())
                .setCurrency(listing.getCurrency())
                .setQuantity(listing.getQuantity())
                .setStartAt(listing.getStartAt())
                .setExpireAt(listing.getExpireAt())
                .setCreatedAt(listing.getCreatedAt())
                .build();
    }


}
