package com.tenth.nft.crawler.dao.expression;

import com.tpulse.gs.convention.dao.SimpleUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;
import com.tpulse.gs.convention.dao.defination.WriteOpt;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
public class NftCategoryUpdate extends SimpleUpdate {

    @SimpleWriteParam
    private String name;
    @SimpleWriteParam
    private Long updatedAt = System.currentTimeMillis();
    @SimpleWriteParam
    private Integer order;

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getName(){
        return name;
    }

    public Integer getOrder() {
        return order;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private NftCategoryUpdate update = new NftCategoryUpdate();

        public Builder setName(String name){
            update.name = name;
            return this;
        }

        public NftCategoryUpdate build(){
            return update;
        }

        public Builder setOrder(Integer order) {
            update.order = order;
            return this;
        }
    }

}
