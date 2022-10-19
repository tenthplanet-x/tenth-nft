package com.tenth.nft.marketplace.common.dto;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
public class NftMyAssetsDTO extends NftAssetsDTO {

    private String collectionName;

    @Override
    public String getCollectionName() {
        return collectionName;
    }

    @Override
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
