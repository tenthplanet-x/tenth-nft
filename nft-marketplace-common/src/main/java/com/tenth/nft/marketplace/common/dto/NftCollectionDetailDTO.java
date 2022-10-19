package com.tenth.nft.marketplace.common.dto;

/**
 * @author shijie
 */
public class NftCollectionDetailDTO extends NftCollectionDTO{

    private String floorPrice;

    private String totalVolume;

    private String currency;

    public String getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(String floorPrice) {
        this.floorPrice = floorPrice;
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
}
