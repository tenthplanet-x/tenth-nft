package com.tenth.nft.operation.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 16:58
 */
@Valid
public class NftCurrencyEditRequest {

    @NotNull
    private Long id;

    private String blockchain;

    private String code;

    private String label;

    private Boolean enable;
    private Integer order;

    public Long getId() {
        return id;
    }

    public String getBlockchain(){
        return blockchain;
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

    public void setBlockchain(String blockchain){
        this.blockchain = blockchain;
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
