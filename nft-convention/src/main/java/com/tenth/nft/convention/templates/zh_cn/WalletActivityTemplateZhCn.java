package com.tenth.nft.convention.templates.zh_cn;

import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.en.WalletActivityTemplateEn;
import com.tpulse.gs.config.TemplateType;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class WalletActivityTemplateZhCn extends WalletActivityTemplateEn {

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.wallet_activity_zh_cn;
    }

    @Override
    public WalletActivityTemplateZhCn newTemplate() {
        return new WalletActivityTemplateZhCn();
    }

}
