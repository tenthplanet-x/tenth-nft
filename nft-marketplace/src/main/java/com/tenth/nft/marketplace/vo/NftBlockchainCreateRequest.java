package com.tenth.nft.marketplace.vo;

import javax.validation.Valid;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Valid
public class NftBlockchainCreateRequest {

    private String code;

    private String label;

    private Boolean enable;

    private Integer order;

    public String getCode(){
        return code;
    }

    public String getLabel(){
        return label;
    }

    public Boolean getEnable(){
        return enable;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public void setEnable(Boolean enable){
        this.enable = enable;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
