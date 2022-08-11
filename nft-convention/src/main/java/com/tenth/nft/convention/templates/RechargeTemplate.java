package com.tenth.nft.convention.templates;

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
public class RechargeTemplate extends AbsTemplate<RechargeConfig> implements GsConfigTemplate {

    private Map<Integer, RechargeConfig> cache = new HashMap<>();

    public RechargeTemplate() {
        super(JsonConfigCodec.INSTANCE, RechargeConfig.class);
    }

    @Override
    protected void afterParseComplete() throws HuaqianguConfigException {

    }

    @Override
    protected void afterParseRecord(RechargeConfig record) throws HuaqianguConfigException {
        cache.put(record.getId(), record);
    }

    @Override
    protected void beforeReload() {

    }

    @Override
    public String getFileName() {
        return null;
    }

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.recharge;
    }
}
