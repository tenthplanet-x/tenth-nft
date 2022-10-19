package com.tenth.nft.wallet.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/08 19:17
 */
public class WalletUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private String value;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getUid(){
        return uid;
    }

    public String getCurrency(){
        return currency;
    }

    public String getValue(){
        return value;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private WalletUpdate update = new WalletUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setCurrency(String currency){
            update.currency = currency;
            return this;
        }

        public Builder setValue(String value){
            update.value = value;
            return this;
        }

        public WalletUpdate build(){
            return update;
        }

    }

}
