package com.tenth.nft.marketplace.common.dao.expression;

import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class AbsNftBuyOrderUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

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

        private AbsNftBuyOrderUpdate update = newUpdate();


        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
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

        public Builder remark(String remark) {
            update.remark = remark;
            return this;
        }

        protected AbsNftBuyOrderUpdate newUpdate() {
            return new AbsNftBuyOrderUpdate();
        }

        public AbsNftBuyOrderUpdate build(){
            return update;
        }

    }

}
