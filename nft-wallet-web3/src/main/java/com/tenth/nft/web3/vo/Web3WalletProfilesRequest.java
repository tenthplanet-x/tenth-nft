package com.tenth.nft.web3.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author shijie
 */
@Valid
public class Web3WalletProfilesRequest {

    @NotNull
    private Long uid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
