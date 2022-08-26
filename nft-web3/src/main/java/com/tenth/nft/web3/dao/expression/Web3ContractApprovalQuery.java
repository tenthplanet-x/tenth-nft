package com.tenth.nft.web3.dao.expression;

import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/26 19:28
 */
public class Web3ContractApprovalQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        Web3ContractApprovalQuery query;

        public Builder() {
            super(new Web3ContractApprovalQuery());
            this.query = (Web3ContractApprovalQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

    }

}
