package com.tenth.nft.exchange.web3.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author shijie
 */
@Valid
public class Web3AcceptCreateRequest {

    @NotNull
    private Long assetsId;
    @NotNull
    private Long offerId;

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }
}