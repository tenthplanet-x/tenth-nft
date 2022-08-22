package com.tenth.nft.convention.templates.en;

import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config2.GsConfigTemplate;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class WalletCurrencyTemplateEn extends WalletCurrencyTemplate {

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.wallet_currency_en;
    }

    @Override
    public GsConfigTemplate newTemplate() {
        return new WalletCurrencyTemplateEn();
    }
}
