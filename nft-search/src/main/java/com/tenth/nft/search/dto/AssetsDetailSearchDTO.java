package com.tenth.nft.search.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.convention.utils.Prices;
import com.tenth.nft.orm.marketplace.entity.NftOffer;
import com.tenth.nft.protobuf.NftExchange;

/**
 * @author shijie
 */
public class AssetsDetailSearchDTO extends AssetsSearchDTO {

    private UserProfileDTO creatorProfile;

    private String totalVolume;

    private String currency;

    private int owns;

    private ListingDTO currentListing;

    //仅只有一个拥有者时，使用
    private UserProfileDTO ownerProfile;

    private int owners;

    private NftOfferDTO bestOffer;

    public UserProfileDTO getCreatorProfile() {
        return creatorProfile;
    }

    public void setCreatorProfile(UserProfileDTO creatorProfile) {
        this.creatorProfile = creatorProfile;
    }

    public String getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(String totalVolume) {
        this.totalVolume = totalVolume;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getOwns() {
        return owns;
    }

    public void setOwns(int owns) {
        this.owns = owns;
    }

    public ListingDTO getCurrentListing() {
        return currentListing;
    }

    public void setCurrentListing(ListingDTO currentListing) {
        this.currentListing = currentListing;
    }

    public UserProfileDTO getOwnerProfile() {
        return ownerProfile;
    }

    public void setOwnerProfile(UserProfileDTO ownerProfile) {
        this.ownerProfile = ownerProfile;
    }

    public int getOwners() {
        return owners;
    }

    public void setOwners(int owners) {
        this.owners = owners;
    }

    public NftOfferDTO getBestOffer() {
        return bestOffer;
    }

    public void setBestOffer(NftOfferDTO bestOffer) {
        this.bestOffer = bestOffer;
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
            dto.setPrice(Prices.toString(listing.getPrice()));
            dto.setQuantity(listing.getQuantity());
            dto.setStartAt(listing.getStartAt());
            dto.setExpireAt(listing.getExpireAt());
            dto.setSeller(listing.getSeller());
            return dto;
        }
    }


    public static class NftOfferDTO {

        private Long id;

        private String price;

        private String currency;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
            AssetsDetailSearchDTO.NftOfferDTO nftOfferDTO = new AssetsDetailSearchDTO.NftOfferDTO();
            nftOfferDTO.setId(nftOffer.getId());
            nftOfferDTO.setPrice(Prices.toString(nftOffer.getPrice()));
            nftOfferDTO.setCurrency(nftOffer.getCurrency());
            return nftOfferDTO;
        }
    }




}
