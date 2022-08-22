package com.tenth.nft.web3.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;
import com.tpulse.gs.convention.dao.dto.Page;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/19 12:00
 */
public class Web3WalletEventQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String blockchain;
    @SimpleQueryParam
    private String accountId;

    public Long getId() {
        return id;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public String getAccountId() {
        return accountId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        Web3WalletEventQuery query;

        public Builder() {
            super(new Web3WalletEventQuery());
            this.query = (Web3WalletEventQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }


        public Builder blockchain(String blockchain) {
            query.blockchain = blockchain;
            return this;
        }

        public Builder accountId(String accountId) {
            query.accountId = accountId;
            return this;
        }
    }

}
