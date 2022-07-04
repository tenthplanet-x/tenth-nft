package com.tenth.nft.crawler.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
public class ExternalNftItemDTO implements SimpleResponse {

    @SimpleField(name = "previewUrl")
    private String url;

    @SimpleField
    private Integer tokenNo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(Integer tokenNo) {
        this.tokenNo = tokenNo;
    }
}
