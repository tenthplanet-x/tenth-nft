package com.tenth.nft.wallet.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/08 19:17
 */
public class WalletBillUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private Long uid;

    @SimpleWriteParam
    private String type;

    @SimpleWriteParam
    private String remark;

    @SimpleWriteParam
    private String productCode;

    @SimpleWriteParam
    private Long productId;

    @SimpleWriteParam
    private Long outOrderId;

    @SimpleWriteParam
    private String merchantType;

    @SimpleWriteParam
    private Long merchantId;

    @SimpleWriteParam
    private String currency;

    @SimpleWriteParam
    private String value;

    @SimpleWriteParam
    private String state;

    @SimpleWriteParam
    private Long expiredAt;

    @SimpleWriteParam
    private String notifyUri;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getUid(){
        return uid;
    }

    public String getType(){
        return type;
    }

    public String getRemark(){
        return remark;
    }

    public String getProductCode(){
        return productCode;
    }

    public Long getProductId(){
        return productId;
    }

    public Long getOutOrderId(){
        return outOrderId;
    }

    public String getMerchantType(){
        return merchantType;
    }

    public Long getMerchantId(){
        return merchantId;
    }

    public String getCurrency(){
        return currency;
    }

    public String getValue(){
        return value;
    }

    public String getState(){
        return state;
    }

    public Long getExpiredAt(){
        return expiredAt;
    }

    public String getNotifyUri(){
        return notifyUri;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private WalletBillUpdate update = new WalletBillUpdate();

        public Builder setUid(Long uid){
            update.uid = uid;
            return this;
        }

        public Builder setType(String type){
            update.type = type;
            return this;
        }

        public Builder setRemark(String remark){
            update.remark = remark;
            return this;
        }

        public Builder setProductCode(String productCode){
            update.productCode = productCode;
            return this;
        }

        public Builder setProductId(Long productId){
            update.productId = productId;
            return this;
        }

        public Builder setOutOrderId(Long outOrderId){
            update.outOrderId = outOrderId;
            return this;
        }

        public Builder setMerchantType(String merchantType){
            update.merchantType = merchantType;
            return this;
        }

        public Builder setMerchantId(Long merchantId){
            update.merchantId = merchantId;
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

        public Builder setState(String state){
            update.state = state;
            return this;
        }

        public Builder setExpiredAt(Long expiredAt){
            update.expiredAt = expiredAt;
            return this;
        }

        public Builder setNotifyUri(String notifyUri){
            update.notifyUri = notifyUri;
            return this;
        }

        public WalletBillUpdate build(){
            return update;
        }

    }

}
