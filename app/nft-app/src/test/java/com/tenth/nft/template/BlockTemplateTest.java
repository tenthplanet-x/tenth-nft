package com.tenth.nft.template;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.convention.templates.BlockchainConfig;
import com.tenth.nft.convention.templates.BlockchainTemplate;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.wallan.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class BlockTemplateTest {

    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    @Test
    public void findOne(){

        BlockchainTemplate template = i18nGsTemplates.get(NftTemplateTypes.blockchain);
        Assert.assertTrue(template.findOne("Ethereum") != null);
        BlockchainConfig config = template.findOne("Ethereum");
        System.out.println(JsonUtil.toJson(config));
    }
}
