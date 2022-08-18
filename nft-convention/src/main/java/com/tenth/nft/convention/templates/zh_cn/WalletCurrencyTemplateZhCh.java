package com.tenth.nft.convention.templates.zh_cn;

import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.convention.templates.en.WalletCurrencyTemplateEn;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config2.GsConfigTemplate;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class WalletCurrencyTemplateZhCh extends WalletCurrencyTemplate {

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.wallet_currency_zh_cn;
    }

    @Override
    public GsConfigTemplate newTemplate() {
        return new WalletCurrencyTemplateEn();
    }
}
