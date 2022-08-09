package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
public class NftCurrencyQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String blockchain;
    @SimpleQueryParam
    private Integer version;
    @SimpleQueryParam
    private Boolean main;
    @SimpleQueryParam
    private String code;

    public Long getId() {
        return id;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public Integer getVersion() {
        return version;
    }

    public Boolean getMain() {
        return main;
    }

    public String getCode() {
        return code;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        NftCurrencyQuery query;

        public Builder() {
            super(new NftCurrencyQuery());
            this.query = (NftCurrencyQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder blockchain(String blockchain) {
            query.blockchain = blockchain;
            return this;
        }

        public Builder version(Integer version) {
            query.version = version;
            return this;
        }

        public Builder main(Boolean main) {
            query.main = main;
            return this;
        }

        public Builder code(String code) {
            query.code = code;
            return this;
        }
    }

}
