package com.tenth.nft.marketplace.common.dao.expression;

import com.tenth.nft.marketplace.common.entity.NftActivityEventType;
import com.tenth.nft.marketplace.common.entity.event.*;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class AbsNftUbtLogUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long assetsId;

    @SimpleWriteParam
    private NftActivityEventType type;

    @SimpleWriteParam
    private MintEvent mint;

    @SimpleWriteParam
    private TransferEvent transfer;

    @SimpleWriteParam
    private ListEvent list;

    @SimpleWriteParam
    private OfferEvent offer;

    @SimpleWriteParam
    private SaleEvent sale;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam
    private Boolean freeze;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getAssetsId(){
        return assetsId;
    }

    public NftActivityEventType getType(){
        return type;
    }

    public MintEvent getMint(){
        return mint;
    }

    public TransferEvent getTransfer(){
        return transfer;
    }

    public ListEvent getList(){
        return list;
    }

    public OfferEvent getOffer(){
        return offer;
    }

    public SaleEvent getSale() {
        return sale;
    }

    public Boolean getFreeze() {
        return freeze;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private AbsNftUbtLogUpdate update = new AbsNftUbtLogUpdate();

        public Builder setAssetsId(Long assetsId){
            update.assetsId = assetsId;
            return this;
        }

        public Builder setType(NftActivityEventType type){
            update.type = type;
            return this;
        }

        public Builder setMint(MintEvent mint){
            update.mint = mint;
            return this;
        }

        public Builder setTransfer(TransferEvent transfer){
            update.transfer = transfer;
            return this;
        }

        public Builder setList(ListEvent list){
            update.list = list;
            return this;
        }

        public Builder setOffer(OfferEvent offer){
            update.offer = offer;
            return this;
        }

        public AbsNftUbtLogUpdate build(){
            return update;
        }

        public Builder freeze(Boolean freeze) {
            update.freeze = freeze;
            return this;
        }
    }

}
