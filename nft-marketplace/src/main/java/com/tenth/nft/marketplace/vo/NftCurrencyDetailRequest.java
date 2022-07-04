package com.tenth.nft.marketplace.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Valid
public class NftCurrencyDetailRequest {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
