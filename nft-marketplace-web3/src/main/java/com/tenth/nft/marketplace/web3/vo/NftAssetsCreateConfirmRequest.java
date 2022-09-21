package com.tenth.nft.marketplace.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * @author shijie
 */
@Valid
public class NftAssetsCreateConfirmRequest {

    @NotEmpty
    private String content;

    @NotEmpty
    private String signature;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
