package com.tenth.nft.wallet.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/08 19:17
 */
public class WalletBillQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long uid;
    @SimpleQueryParam
    private String productCode;
    @SimpleQueryParam
    private Long outOrderId;

    public Long getId() {
        return id;
    }

    public Long getUid() {
        return uid;
    }

    public String getProductCode() {
        return productCode;
    }

    public Long getOutOrderId() {
        return outOrderId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        WalletBillQuery query;

        public Builder() {
            super(new WalletBillQuery());
            this.query = (WalletBillQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder uid(Long uid) {
            query.uid = uid;
            return this;
        }

        public Builder productCode(String productCode) {
            query.productCode = productCode;
            return this;
        }

        public Builder outOrderId(Long outOrderId) {
            query.outOrderId = outOrderId;
            return this;
        }
    }

}
