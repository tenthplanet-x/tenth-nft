package com.tenth.nft.marketplace.vo;

import com.tenth.nft.convention.web3.utils.TokenMintStatus;

/**
 * @author shijie
 */
public class NftAssetsMintCheckResponse {

    private TokenMintStatus status;

    public NftAssetsMintCheckResponse(){

    }

    public NftAssetsMintCheckResponse(TokenMintStatus status) {
        this.status = status;
    }

    public TokenMintStatus getStatus() {
        return status;
    }

    public void setStatus(TokenMintStatus status) {
        this.status = status;
    }
}
