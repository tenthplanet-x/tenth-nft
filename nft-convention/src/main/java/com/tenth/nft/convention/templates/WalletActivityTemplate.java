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
public class WalletActivityTemplate extends AbsTemplate<WalletActivityConfig> implements GsConfigTemplate {

    private Map<Integer, WalletActivityConfig> cache = new HashMap<>();

    public WalletActivityTemplate() {
        super(JsonConfigCodec.INSTANCE, WalletActivityConfig.class);
    }

    @Override
    protected void afterParseComplete() throws HuaqianguConfigException {

    }

    @Override
    protected void afterParseRecord(WalletActivityConfig record) throws HuaqianguConfigException {
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
        return NftTemplateTypes.wallet_activity;
    }

    public WalletActivityConfig findOne(Integer activityCfgId) {
        return optionalOf(activityCfgId, cache).get();
    }
}
