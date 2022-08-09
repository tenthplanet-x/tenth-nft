package com.tenth.nft.wallet.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/08 19:17
 */
public class WalletSettingUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private String password;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getUid(){
        return uid;
    }

    public String getPassword(){
        return password;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private WalletSettingUpdate update = new WalletSettingUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setPassword(String password){
            update.password = password;
            return this;
        }

        public WalletSettingUpdate build(){
            return update;
        }

    }

}
