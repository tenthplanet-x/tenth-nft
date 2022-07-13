package com.tenth.nft.orm.marketplace.dao.expression;

import com.tenth.nft.orm.marketplace.entity.NftActivity;
import com.tenth.nft.orm.marketplace.entity.NftActivityEventType;
import com.tenth.nft.orm.marketplace.entity.event.*;
import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/05 12:01
 */
public class NftActivityUpdate extends SimpleUpdate {

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

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftActivityUpdate update = new NftActivityUpdate();

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

        public NftActivityUpdate build(){
            return update;
        }

    }

}
