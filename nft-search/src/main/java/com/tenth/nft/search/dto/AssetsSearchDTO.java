package com.tenth.nft.search.dto;

import com.google.common.base.Strings;
import com.ruixi.tpulse.convention.protobuf.app.AppChat;
import com.tenth.nft.orm.marketplace.entity.NftAssetsType;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author shijie
 */
public class AssetsSearchDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private NftAssetsType type;

    @SimpleField
    private Long collectionId;

    @SimpleField
    private String url;

    @SimpleField
    private String previewUrl;

    @SimpleField
    private String name;

    @SimpleField
    private String desc;

    @SimpleField
    private Integer supply;

    @SimpleField
    private String blockchain;

    @SimpleField
    private String contractAddress;

    @SimpleField
    private String tokenStandard;

    @SimpleField
    private String token;

    @SimpleField
    private Long creator;

    private AssetsDetailSearchDTO.ListingDTO currentListing;

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

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getSupply() {
        return supply;
    }

    public void setSupply(Integer supply) {
        this.supply = supply;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getTokenStandard() {
        return tokenStandard;
    }

    public void setTokenStandard(String tokenStandard) {
        this.tokenStandard = tokenStandard;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public AssetsDetailSearchDTO.ListingDTO getCurrentListing() {
        return currentListing;
    }

    public void setCurrentListing(AssetsDetailSearchDTO.ListingDTO currentListing) {
        this.currentListing = currentListing;
    }

    public static AssetsSearchDTO from(NftMarketplace.AssetsDTO assets) {
        return from(assets, AssetsSearchDTO.class);
    }

    public static <T extends AssetsSearchDTO> T from(NftMarketplace.AssetsDTO assets, Class<T> collection) {
        AssetsSearchDTO output = null;
        try {
            output = collection.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            throw new RuntimeException("", e);
        }
        output.setId(assets.getId());
        output.setType(NftAssetsType.valueOf(assets.getType()));
        output.setCollectionId(assets.getCollectionId());
        output.setUrl(assets.getUrl());
        output.setPreviewUrl(Strings.emptyToNull(assets.getPreviewUrl()));
        output.setBlockchain(assets.getBlockchain());
        output.setContractAddress(Strings.emptyToNull(assets.getContractAddress()));
        output.setTokenStandard(Strings.emptyToNull(assets.getTokenStandard()));
        output.setToken(Strings.emptyToNull(assets.getToken()));
        output.setCreator(assets.getCreator());
        output.setName(assets.getName());
        output.setSupply(assets.getSupply());
        output.setDesc(Strings.emptyToNull(assets.getDesc()));
        return (T)output;
    }
}
