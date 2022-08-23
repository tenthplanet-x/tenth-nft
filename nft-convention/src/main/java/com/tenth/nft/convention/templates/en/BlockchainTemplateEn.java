package com.tenth.nft.convention.templates.en;

import com.tenth.nft.convention.templates.BlockchainTemplate;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config2.GsConfigTemplate;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BlockchainTemplateEn extends BlockchainTemplate {

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.blockchain_en;
    }

    @Override
    public GsConfigTemplate newTemplate() {
        return new BlockchainTemplateEn();
    }
}
