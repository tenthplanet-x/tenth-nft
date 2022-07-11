package com.tenth.nft.search.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;

/**
 * @author shijie
 */
public class AssetsDetailSearchDTO extends AssetsSearchDTO {

    private UserProfileDTO creatorProfile;

    private String currentPrice;

    private String totalVolume;

    private String currency;

    private int owns;

    public UserProfileDTO getCreatorProfile() {
        return creatorProfile;
    }

    public void setCreatorProfile(UserProfileDTO creatorProfile) {
        this.creatorProfile = creatorProfile;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
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
}
