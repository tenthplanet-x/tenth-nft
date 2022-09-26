package com.tenth.nft.marketplace.common.vo;

import com.tpulse.commons.biz.dto.PageRequest;

/**
 * @author shijie
 */
public class NftOwnerListRequest extends PageRequest {

    private Long assetsId;

    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }
}
