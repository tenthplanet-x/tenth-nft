package com.tenth.nft.marketplace.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Valid
public class NftCategoryDetailRequest {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
