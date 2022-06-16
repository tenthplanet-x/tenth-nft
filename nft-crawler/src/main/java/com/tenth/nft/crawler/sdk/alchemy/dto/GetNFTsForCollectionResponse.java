package com.tenth.nft.crawler.sdk.alchemy.dto;

import java.util.List;

/**
 * @author shijie
 * @createdAt 2022/6/14 19:28
 */
public class GetNFTsForCollectionResponse {

    private List<AlchemyNftDTO> nfts;

    public List<AlchemyNftDTO> getNfts() {
        return nfts;
    }

    public void setNfts(List<AlchemyNftDTO> nfts) {
        this.nfts = nfts;
    }
}
