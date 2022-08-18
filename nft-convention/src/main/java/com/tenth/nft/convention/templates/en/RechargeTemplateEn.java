package com.tenth.nft.convention.templates.en;

import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.RechargeConfig;
import com.tenth.nft.convention.templates.RechargeTemplate;
import com.tpulse.gs.config.ConfigCodec;
import com.tpulse.gs.config.HuaqianguConfigException;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config.codec.JsonConfigCodec;
import com.tpulse.gs.config.template.AbsTemplate;
import com.tpulse.gs.config2.GsConfigTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shijie
 */
@Component
public class RechargeTemplateEn extends RechargeTemplate {

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.recharge_en;
    }

    @Override
    public GsConfigTemplate newTemplate() {
        return new RechargeTemplateEn();
    }
}
