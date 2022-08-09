package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.external.dao.expression.ExternalNftCategoryUpdate;
import com.tenth.nft.orm.marketplace.entity.NftOrderStatus;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class NftOrderUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private Long listingId;

    @SimpleWriteParam
    private Long buyer;

    @SimpleWriteParam
    private NftOrderStatus status;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private String remark;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public Long getListingId(){
        return listingId;
    }

    public Long getBuyer(){
        return buyer;
    }

    public NftOrderStatus getStatus(){
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftOrderUpdate update = new NftOrderUpdate();

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setListingId(Long listingId){
            update.listingId = listingId;
            return this;
        }

        public Builder setBuyer(Long buyer){
            update.buyer = buyer;
            return this;
        }

        public Builder setStatus(NftOrderStatus status){
            update.status = status;
            return this;
        }

        public NftOrderUpdate build(){
            return update;
        }

        public Builder remark(String remark) {
            update.remark = remark;
            return this;
        }
    }

}
