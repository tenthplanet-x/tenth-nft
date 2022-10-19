package com.tenth.nft.marketplace.buildin.vo;

import com.tenth.nft.marketplace.common.vo.NftOfferAcceptRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author shijie
 */
@Valid
public class BuildinNftOfferAcceptRequest extends NftOfferAcceptRequest {

    @NotEmpty
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
