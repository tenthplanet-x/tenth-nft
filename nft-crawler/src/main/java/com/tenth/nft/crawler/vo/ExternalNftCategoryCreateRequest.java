package com.tenth.nft.crawler.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/17 15:00
 */
@Valid
public class ExternalNftCategoryCreateRequest {

    @NotEmpty
    private String name;

    @NotNull
    private Integer order;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
