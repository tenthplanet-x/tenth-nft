package com.tenth.nft.web3.dao.expression;

import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/19 12:00
 */
public class Web3WalletBillQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private String accountId;
    @SimpleQueryParam
    private String productCode;
    @SimpleQueryParam
    private String outOrderId;
    @SimpleQueryParam
    private String blockchain;
    @SimpleQueryParam
    private String productId;

    public Long getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public String getProductId() {
        return productId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        Web3WalletBillQuery query;

        public Builder() {
            super(new Web3WalletBillQuery());
            this.query = (Web3WalletBillQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder accountId(String accountId) {
            query.accountId = accountId;
            return this;
        }

        public Builder productCode(String productCode) {
            query.productCode = productCode;
            return this;
        }

        public Builder outOrderId(String outOrderId) {
            query.outOrderId = outOrderId;
            return this;
        }

        public Builder blockchain(String blockchain) {
            query.blockchain = blockchain;
            return this;
        }

        public Builder productId(String productId) {
            query.productId = productId;
            return this;
        }
    }

}
