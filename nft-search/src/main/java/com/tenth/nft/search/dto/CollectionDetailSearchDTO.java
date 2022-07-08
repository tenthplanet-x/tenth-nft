package com.tenth.nft.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;


/**
 * @author shijie
 */
public class CollectionDetailSearchDTO extends CollectionSearchDTO {

    private String floorPrice;

    private String totalVolume;

    private String currency;

    @Override
    public String getFloorPrice() {
        return floorPrice;
    }

    @Override
    public void setFloorPrice(String floorPrice) {
        this.floorPrice = floorPrice;
    }

    @Override
    public String getTotalVolume() {
        return totalVolume;
    }

    @Override
    public void setTotalVolume(String totalVolume) {
        this.totalVolume = totalVolume;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
