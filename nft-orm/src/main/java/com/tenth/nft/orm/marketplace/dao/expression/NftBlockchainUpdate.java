package com.tenth.nft.orm.marketplace.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
public class NftBlockchainUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String code;

    @SimpleWriteParam
    private String label;

    @SimpleWriteParam
    private Boolean enable;

    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();

    @SimpleWriteParam
    private Integer order;

    @SimpleWriteParam
    private String icon;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getCode(){
        return code;
    }

    public String getLabel(){
        return label;
    }

    public Boolean getEnable(){
        return enable;
    }

    public Integer getOrder() {
        return order;
    }

    public String getIcon() {
        return icon;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftBlockchainUpdate update = new NftBlockchainUpdate();

        public Builder setCode(String code){
            update.code = code;
            return this;
        }

        public Builder setLabel(String label){
            update.label = label;
            return this;
        }

        public Builder setEnable(Boolean enable){
            update.enable = enable;
            return this;
        }

        public NftBlockchainUpdate build(){
            return update;
        }

        public Builder order(Integer order) {
            update.order = order;
            return this;
        }

        public Builder setIcon(String icon) {
            update.icon = icon;
            return this;
        }
    }

}
