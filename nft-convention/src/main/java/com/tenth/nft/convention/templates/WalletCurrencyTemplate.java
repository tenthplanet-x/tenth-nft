package com.tenth.nft.convention.templates;

import com.tpulse.gs.config.ConfigCodec;
import com.tpulse.gs.config.HuaqianguConfigException;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config.codec.JsonConfigCodec;
import com.tpulse.gs.config.template.AbsTemplate;
import com.tpulse.gs.config2.GsConfigTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Component
public class WalletCurrencyTemplate extends AbsTemplate<WalletCurrencyConfig> implements GsConfigTemplate {

    private List<WalletCurrencyConfig> cache = new ArrayList<>();

    public WalletCurrencyTemplate() {
        super(JsonConfigCodec.INSTANCE, WalletCurrencyConfig.class);
    }

    @Override
    protected void afterParseComplete() throws HuaqianguConfigException {

    }

    @Override
    protected void afterParseRecord(WalletCurrencyConfig record) throws HuaqianguConfigException {
        cache.add(record);
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
        return NftTemplateTypes.wallet_currency;
    }

    public List<WalletCurrencyConfig> findByBlockchain(String blockchain) {
        return cache.stream()
                .filter(config -> config.getBlockchain().equals(blockchain))
                .sorted(Comparator.comparingInt(WalletCurrencyConfig::getId))
                .collect(Collectors.toList());
    }
}
