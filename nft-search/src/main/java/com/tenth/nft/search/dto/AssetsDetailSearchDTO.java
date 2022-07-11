package com.tenth.nft.search.dto;

import com.ruixi.tpulse.convention.vo.UserProfileDTO;
import com.tenth.nft.search.dto.AssetsSearchDTO;

/**
 * @author shijie
 */
public class AssetsDetailSearchDTO extends AssetsSearchDTO {

    private UserProfileDTO creatorProfile;

    private String floorPrice;

    private String totalVolume;

    private String currency;

    private int owns;

    public UserProfileDTO getCreatorProfile() {
        return creatorProfile;
    }

    public void setCreatorProfile(UserProfileDTO creatorProfile) {
        this.creatorProfile = creatorProfile;
    }

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

    public int getOwns() {
        return owns;
    }

    public void setOwns(int owns) {
        this.owns = owns;
    }
}
