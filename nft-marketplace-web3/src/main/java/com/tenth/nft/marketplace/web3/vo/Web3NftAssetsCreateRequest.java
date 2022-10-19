package com.tenth.nft.marketplace.web3.vo;

import com.tenth.nft.marketplace.common.vo.NftAssetsCreateRequest;

/**
 * @author shijie
 */
public class Web3NftAssetsCreateRequest extends NftAssetsCreateRequest {

    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
