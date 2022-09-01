package com.tenth.nft.convention.templates.zh_cn;

import com.tenth.nft.convention.templates.BlockchainTemplate;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tpulse.gs.config.TemplateType;
import com.tpulse.gs.config2.GsConfigTemplate;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BlockchainTemplateZhCn extends BlockchainTemplate {

    @Override
    public TemplateType getType() {
        return NftTemplateTypes.blockchain_zh_cn;
    }

    @Override
    public GsConfigTemplate newTemplate() {
        return new BlockchainTemplateZhCn();
    }
}
