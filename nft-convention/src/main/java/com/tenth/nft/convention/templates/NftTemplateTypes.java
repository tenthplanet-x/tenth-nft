package com.tenth.nft.convention.templates;

import com.tpulse.gs.config.TemplateType;

/**
 * @author shijie
 */
public enum NftTemplateTypes implements TemplateType {

    wallet_activity,
    wallet_activity_en,
    wallet_activity_zh_cn("wallet_activity_zh-cn"),
    recharge,
    recharge_en,
    recharge_zh_cn("recharge_zh-cn"),
    wallet_currency,
    wallet_currency_en,
    wallet_currency_zh_cn("wallet_currency_zh-cn"),
    blockchain,
    blockchain_en,
    blockchain_zh_cn("blockchain_zh-cn"),
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
