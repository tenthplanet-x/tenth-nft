package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/15 14:07
 */
public class NftCollectionStatsQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String contractAddress;
    @SimpleQueryParam
    private String date;

    public Long getId() {
        return id;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public String getDate() {
        return date;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftCollectionStatsQuery query;

        public Builder() {
            super(new NftCollectionStatsQuery());
            this.query = (NftCollectionStatsQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder contractAddress(String contractAddress) {
            query.contractAddress = contractAddress;
            return this;
        }

        public Builder date(String date) {
            query.date = date;
            return this;
        }
    }

}
