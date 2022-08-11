package com.tenth.nft.convention.templates.zh_cn;

import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.en.RechargeTemplateEn;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config2.GsConfigTemplate;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class RechargeTemplateZhCn extends RechargeTemplateEn {

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.recharge_zh_cn;
    }

    @Override
    public GsConfigTemplate newTemplate() {
        return new RechargeTemplateZhCn();
    }
}
