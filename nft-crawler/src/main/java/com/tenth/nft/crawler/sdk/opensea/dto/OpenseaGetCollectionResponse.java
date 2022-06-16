package com.tenth.nft.crawler.sdk.opensea.dto;


/**
 * @author shijie
 * @createdAt 2022/6/15 11:10
 */
public class OpenseaGetCollectionResponse {

    private OpenseaCollectionDTO collection;

    public OpenseaCollectionDTO getCollection() {
        return collection;
    }

    public void setCollection(OpenseaCollectionDTO collection) {
        this.collection = collection;
    }
}
