package com.tenth.nft.search.dto;

import java.util.List;

/**
 * @author shijie
 */
public class CollectionSearchDTO {

    private String name;

    private String logoImage;

    private String featuredImage;

    private String bannerImage;

    private List<ItemSearchDTO> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public List<ItemSearchDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemSearchDTO> items) {
        this.items = items;
    }
}
