package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tenth.nft.orm.marketplace.entity.NftOrderStatus;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.annotation.SimpleQueryParam;
import com.tpulse.gs.convention.dao.defination.QueryOpt;

import java.awt.image.PixelGrabber;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class NftOrderQuery extends SimplePageQuery {

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

        NftOrderQuery query;

        public Builder() {
            super(new NftOrderQuery());
            this.query = (NftOrderQuery) super.query;
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
