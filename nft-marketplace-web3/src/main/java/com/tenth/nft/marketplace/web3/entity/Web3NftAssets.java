package com.tenth.nft.marketplace.web3.entity;

import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;

/**
 * @author shijie
 */
public class Web3NftAssets extends AbsNftAssets {

    private TokenMintStatus mintStatus;

    private String signature;

    public TokenMintStatus getMintStatus() {
        return mintStatus;
    }

    public void setMintStatus(TokenMintStatus mintStatus) {
        this.mintStatus = mintStatus;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
