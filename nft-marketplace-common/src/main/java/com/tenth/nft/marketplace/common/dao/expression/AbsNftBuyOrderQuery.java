package com.tenth.nft.marketplace.common.dao.expression;

import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class AbsNftBuyOrderQuery extends SimplePageQuery {

    @SimpleQueryParam(name = "_id")
    private Long id;
    @SimpleQueryParam
    private Long assetsId;
    @SimpleQueryParam
    private Long buyer;
    @SimpleQueryParam
    private NftOrderStatus status;
    @SimpleQueryParam
    private Long owner;

    public Long getId() {
        return id;
    }

    public Long getAssetsId() {
        return assetsId;
    }

    public Long getBuyer() {
        return buyer;
    }

    public NftOrderStatus getStatus() {
        return status;
    }

    public Long getOwner() {
        return owner;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends SimplePageQuery.Builder{

        AbsNftBuyOrderQuery query;

        public Builder() {
            super(new AbsNftBuyOrderQuery());
            this.query = (AbsNftBuyOrderQuery) super.query;
        }

        public Builder id(Long id) {
            query.id = id;
            return this;
        }

        public Builder assetsId(Long assetsId) {
            query.assetsId = assetsId;
            return this;
        }

        public Builder buyer(Long owner) {
            query.buyer = owner;
            return this;
        }

        public Builder status(NftOrderStatus status) {
            query.status = status;
            return this;
        }

        public Builder owner(Long owner) {
            query.owner = owner;
            return this;
        }


    }

}
