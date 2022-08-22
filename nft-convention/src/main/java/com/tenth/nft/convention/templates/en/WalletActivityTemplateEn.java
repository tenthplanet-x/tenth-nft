package com.tenth.nft.convention.templates.en;

import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletActivityTemplate;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config2.GsConfigTemplate;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class WalletActivityTemplateEn extends WalletActivityTemplate {

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.wallet_activity_en;
    }

    @Override
    public GsConfigTemplate newTemplate() {
        return new WalletActivityTemplateEn();
    }
}
