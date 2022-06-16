package com.tenth.nft.crawler.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
@Valid
public class NftCollectionEditRequest {

    @NotNull
    private Long id;

    private String name;

    private String logoImage;

    private String featuredImage;

    private String bannerImage;

    public Long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getLogoImage(){
        return logoImage;
    }

    public String getFeaturedImage(){
        return featuredImage;
    }

    public String getBannerImage(){
        return bannerImage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLogoImage(String logoImage){
        this.logoImage = logoImage;
    }

    public void setFeaturedImage(String featuredImage){
        this.featuredImage = featuredImage;
    }

    public void setBannerImage(String bannerImage){
        this.bannerImage = bannerImage;
    }
}
