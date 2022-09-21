package com.tenth.nft.marketplace.common.vo;

import com.tpulse.commons.biz.dto.PageRequest;

import javax.validation.Valid;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@Valid
public class NftCollectionListRequest extends PageRequest {

    @Override
    public int getPageSize() {
        return 10;
    }
}
