package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
public class NftBlockchainQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Integer version;
    @SimpleQueryParam
    private String code;

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getCode() {
        return code;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftBlockchainQuery query;

        public Builder() {
            super(new NftBlockchainQuery());
            this.query = (NftBlockchainQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder version(Integer version) {
            query.version = version;
            return this;
        }

        public Builder code(String code) {
            query.code = code;
            return this;
        }
    }

}
