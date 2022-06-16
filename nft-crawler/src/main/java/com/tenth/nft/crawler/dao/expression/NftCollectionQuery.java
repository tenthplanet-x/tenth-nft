package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/14 14:50
 */
public class NftCollectionQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String contractAddress;

    public Long getId() {
        return id;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftCollectionQuery query;

        public Builder() {
            super(new NftCollectionQuery());
            this.query = (NftCollectionQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder contractAddress(String contractAddress) {
            query.contractAddress = contractAddress;
            return this;
        }
    }

}
