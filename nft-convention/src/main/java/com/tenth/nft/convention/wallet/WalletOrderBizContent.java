package com.tenth.nft.convention.wallet;

import com.ruixi.tpulse.convention.utils.Validations;

import java.util.List;

/**
 * @author shijie
 */
public class WalletOrderBizContent {

    private Integer activityCfgId;
    private String productCode;
    private String productId;
    private String outOrderId;
    private String merchantType;
    private String merchantId;
    private Long expiredAt;
    private String currency;
    private String value;
    private String remark;
    private List<Profit> profits;

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

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getActivityCfgId() {
        return activityCfgId;
    }

    public void setActivityCfgId(Integer activityCfgId) {
        this.activityCfgId = activityCfgId;
    }

    public List<Profit> getProfits() {
        return profits;
    }

    public void setProfits(List<Profit> profits) {
        this.profits = profits;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder{

        private WalletOrderBizContent content = new WalletOrderBizContent();

        public Builder activityCfgId(Integer activityCfgId){
            content.activityCfgId = activityCfgId;
            return this;
        }

        public Builder productCode(String productCode) {
            content.productCode = productCode;
            return this;
        }

        public Builder productId(String productId) {
            content.productId = productId;
            return this;
        }

        public Builder outOrderId(String outOrderId) {
            content.outOrderId = outOrderId;
            return this;
        }

        public Builder merchantType(String merchantType) {
            content.merchantType = merchantType;
            return this;
        }

        public Builder merchantId(String merchantId) {
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

        public Builder profits(List<Profit> profits){
            content.profits = profits;
            return this;
        }

        public WalletOrderBizContent build() {
            return content;
        }
    }

    public static class Profit{

        private String to;

        private Integer activityCfgId;

        private String currency;

        private String value;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Integer getActivityCfgId() {
            return activityCfgId;
        }

        public void setActivityCfgId(Integer activityCfgId) {
            this.activityCfgId = activityCfgId;
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
    }
}
