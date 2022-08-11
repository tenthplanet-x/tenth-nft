package com.tenth.nft.convention.templates;

import com.tpulse.gs.config.TemplateType;

/**
 * @author shijie
 */
public enum NftTemplateTypes implements TemplateType {

    wallet_activity,
    recharge,
    wallet_activity_en,
    wallet_activity_zh_cn("wallet_activity_zh-cn"),
    recharge_en,
    recharge_zh_cn("recharge_zh-cn")
    ;


    private String name;
    NftTemplateTypes(String name) {
        this.name = name;
    }

    NftTemplateTypes(){
        this.name = this.name();
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return name;
    }
}
