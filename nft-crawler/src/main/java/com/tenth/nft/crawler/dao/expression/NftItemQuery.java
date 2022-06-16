package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
public class NftItemQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String contractAddress;
    @SimpleQueryParam
    private String tokenId;
    @SimpleQueryParam
    private Integer tokenNo;

    public Long getId() {
        return id;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public String getTokenId() {
        return tokenId;
    }

    public Integer getTokenNo() {
        return tokenNo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftItemQuery query;

        public Builder() {
            super(new NftItemQuery());
            this.query = (NftItemQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder contractAddress(String contractAddress) {
            query.contractAddress = contractAddress;
            return this;
        }

        public Builder tokenId(String tokenId) {
            query.tokenId = tokenId;
            return this;
        }

        public Builder tokenNo(Integer tokenNo) {
            query.tokenNo = tokenNo;
            return this;
        }
    }

}
