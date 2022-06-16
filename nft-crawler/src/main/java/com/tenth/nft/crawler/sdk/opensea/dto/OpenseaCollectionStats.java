package com.tenth.nft.crawler.sdk.opensea.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shijie
 * @createdAt 2022/6/15 11:12
 */
public class OpenseaCollectionStats {

    @JsonProperty("one_day_volume")
    private float oneDayVolume;
    @JsonProperty("one_day_change")
    private float oneDayChange;
    @JsonProperty("one_day_sales")
    private float oneDaySales;
    @JsonProperty("one_day_average_price")
    private float oneDayAveragePrice;
    @JsonProperty("seven_day_volume")
    private float sevenDayVolume;
    @JsonProperty("seven_day_change")
    private float sevenDayChange;
    @JsonProperty("seven_day_sales")
    private float sevenDaySales;
    @JsonProperty("seven_day_average_price")
    private float sevenDayAveragePrice;
    @JsonProperty("thirty_day_volume")
    private float thirtyDayVolume;
    @JsonProperty("thirty_day_change")
    private float thirtyDayChange;
    @JsonProperty("thirty_day_sales")
    private float thirtyDaySales;
    @JsonProperty("thirty_day_average_price")
    private float thirtyDayAveragePrice;
    @JsonProperty("total_volume")
    private float totalVolume;
    @JsonProperty("total_sales")
    private float totalSales;
    @JsonProperty("total_supply")
    private Integer totalSupply;
    @JsonProperty("floor_price")
    private float floorPrice;

    public float getOneDayVolume() {
        return oneDayVolume;
    }

    public void setOneDayVolume(float oneDayVolume) {
        this.oneDayVolume = oneDayVolume;
    }

    public float getOneDayChange() {
        return oneDayChange;
    }

    public void setOneDayChange(float oneDayChange) {
        this.oneDayChange = oneDayChange;
    }

    public float getOneDaySales() {
        return oneDaySales;
    }

    public void setOneDaySales(float oneDaySales) {
        this.oneDaySales = oneDaySales;
    }

    public float getOneDayAveragePrice() {
        return oneDayAveragePrice;
    }

    public void setOneDayAveragePrice(float oneDayAveragePrice) {
        this.oneDayAveragePrice = oneDayAveragePrice;
    }

    public float getSevenDayVolume() {
        return sevenDayVolume;
    }

    public void setSevenDayVolume(float sevenDayVolume) {
        this.sevenDayVolume = sevenDayVolume;
    }

    public float getSevenDayChange() {
        return sevenDayChange;
    }

    public void setSevenDayChange(float sevenDayChange) {
        this.sevenDayChange = sevenDayChange;
    }

    public float getSevenDaySales() {
        return sevenDaySales;
    }

    public void setSevenDaySales(float sevenDaySales) {
        this.sevenDaySales = sevenDaySales;
    }

    public float getSevenDayAveragePrice() {
        return sevenDayAveragePrice;
    }

    public void setSevenDayAveragePrice(float sevenDayAveragePrice) {
        this.sevenDayAveragePrice = sevenDayAveragePrice;
    }

    public float getThirtyDayVolume() {
        return thirtyDayVolume;
    }

    public void setThirtyDayVolume(float thirtyDayVolume) {
        this.thirtyDayVolume = thirtyDayVolume;
    }

    public float getThirtyDayChange() {
        return thirtyDayChange;
    }

    public void setThirtyDayChange(float thirtyDayChange) {
        this.thirtyDayChange = thirtyDayChange;
    }

    public float getThirtyDaySales() {
        return thirtyDaySales;
    }

    public void setThirtyDaySales(float thirtyDaySales) {
        this.thirtyDaySales = thirtyDaySales;
    }

    public float getThirtyDayAveragePrice() {
        return thirtyDayAveragePrice;
    }

    public void setThirtyDayAveragePrice(float thirtyDayAveragePrice) {
        this.thirtyDayAveragePrice = thirtyDayAveragePrice;
    }

    public float getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(float totalVolume) {
        this.totalVolume = totalVolume;
    }

    public float getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(float totalSales) {
        this.totalSales = totalSales;
    }

    public Integer getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(Integer totalSupply) {
        this.totalSupply = totalSupply;
    }

    public float getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(float floorPrice) {
        this.floorPrice = floorPrice;
    }
}
