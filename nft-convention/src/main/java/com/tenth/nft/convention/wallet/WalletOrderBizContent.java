package com.tenth.nft.convention.wallet;

import com.ruixi.tpulse.convention.utils.Validations;

/**
 * @author shijie
 */
public class WalletOrderBizContent {

    private String type;
    private String productCode;
    private Long productId;
    private Long outOrderId;
    private String merchantType;
    private Long merchantId;
    private Long expiredAt;
    private String currency;
    private String value;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(Long outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Long expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder{

        private WalletOrderBizContent content = new WalletOrderBizContent();

        public Builder type(String type){
            content.type = type;
            return this;
        }

        public Builder productCode(String productCode) {
            content.productCode = productCode;
            return this;
        }

        public Builder productId(Long productId) {
            content.productId = productId;
            return this;
        }

        public Builder outOrderId(Long outOrderId) {
            content.outOrderId = outOrderId;
            return this;
        }

        public Builder merchantType(String merchantType) {
            content.merchantType = merchantType;
            return this;
        }

        public Builder merchantId(Long merchantId) {
            content.merchantId = merchantId;
            return this;
        }

        public Builder currency(String currency) {
            content.currency = currency;
            return this;
        }

        public Builder value(String value) {
            content.value = value;
            return this;
        }

        public Builder expiredAt(Long expiredAt) {
            content.expiredAt = expiredAt;
            return this;
        }

        public Builder remark(String remark) {
            content.remark = remark;
            return this;
        }

        public WalletOrderBizContent build() {
            return content;
        }
    }
}
