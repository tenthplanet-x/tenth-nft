package com.tenth.nft.marketplace.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Valid
public class NftBlockchainListRequest extends PageRequest {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
