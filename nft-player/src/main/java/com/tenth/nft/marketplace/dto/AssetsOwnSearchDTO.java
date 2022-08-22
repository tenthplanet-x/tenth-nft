package com.tenth.nft.marketplace.dto;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.orm.marketplace.entity.NftAssetsType;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author shijie
 */
public class AssetsOwnSearchDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private NftAssetsType type;

    @SimpleField
    private String url;

    @SimpleField
    private String previewUrl;

    private ListingDTO currentListing;

    @SimpleField
    private String name;

    @SimpleField
    private Long collectionId;

    private String collectionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NftAssetsType getType() {
        return type;
    }

    public void setType(NftAssetsType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public ListingDTO getCurrentListing() {
        return currentListing;
    }

    public void setCurrentListing(ListingDTO currentListing) {
        this.currentListing = currentListing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public static AssetsOwnSearchDTO from(NftMarketplace.AssetsDTO assets) {

        AssetsOwnSearchDTO output = new AssetsOwnSearchDTO();
        output.setId(assets.getId());
        output.setType(NftAssetsType.valueOf(assets.getType()));
        output.setCollectionId(assets.getCollectionId());
        output.setUrl(assets.getUrl());
        output.setPreviewUrl(Strings.emptyToNull(assets.getPreviewUrl()));
        output.setName(assets.getName());
        return output;

    }

    public static class ListingDTO{

        private Long id;

        private Long assetsId;

        private String currency;

        private String price;

        private Integer quantity;

        private Long seller;

        private UserProfileDTO sellerProfile;

        private Long startAt;

        private Long expireAt;

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

        public Long getAssetsId() {
            return assetsId;
        }

        public void setAssetsId(Long assetsId) {
            this.assetsId = assetsId;
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

        public static ListingDTO from(NftExchange.NftListingDTO listing) {
            ListingDTO dto = new ListingDTO();
            dto.setId(listing.getId());
            dto.setCurrency(listing.getCurrency());
            dto.setExpireAt(listing.getExpireAt());
            dto.setPrice(listing.getPrice());
            dto.setQuantity(listing.getQuantity());
            dto.setStartAt(listing.getStartAt());
            dto.setExpireAt(listing.getExpireAt());
            dto.setSeller(listing.getSeller());
            return dto;
        }
    }
}
