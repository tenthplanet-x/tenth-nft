package com.tenth.nft.web3.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

import java.util.List;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/18 15:53
 */
public class Web3WalletQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long uid;
    @SimpleQueryParam
    private String walletAccountId;
    @SimpleQueryParam(name = "walletAccountId", opt = QueryOpt.IN)
    private List<String> walletAccountIdIn;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }

    public String getWalletAccountId() {
        return walletAccountId;
    }

    public List<String> getWalletAccountIdIn() {
        return walletAccountIdIn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        Web3WalletQuery query;

        public Builder() {
            super(new Web3WalletQuery());
            this.query = (Web3WalletQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }

        public Builder walletAccountId(String walletAccountId) {
            query.walletAccountId = walletAccountId;
            return this;
        }

        public Builder walletAccountIdIn(List<String> addressesList) {
            query.walletAccountIdIn = addressesList;
            return this;
        }
    }

}
