package com.tenth.nft.marketplace.dto;

import com.tpulse.gs.convention.dao.SimpleResponse;
import com.tpulse.gs.convention.dao.annotation.SimpleField;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
public class NftBlockchainDTO implements SimpleResponse {

    @SimpleField(name = "_id")
    public Long id;

    @SimpleField
    private String code;

    @SimpleField
    private String label;

    @SimpleField
    private Boolean enable;

    @SimpleField
    private Integer order;

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
