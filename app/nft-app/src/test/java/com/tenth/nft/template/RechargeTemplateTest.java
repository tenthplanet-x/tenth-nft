package com.tenth.nft.template;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.RechargeTemplate;
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
public class RechargeTemplateTest {

    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    @Test
    public void test(){

        RechargeTemplate rechargeTemplate = i18nGsTemplates.get(NftTemplateTypes.recharge);
        Integer amount = rechargeTemplate.findOneByProductId("com.tplanet.product.nft.tpc.coin.1").getAmount();
        String currency = rechargeTemplate.findOneByProductId("com.tplanet.product.nft.tpc.coin.1").getCurrency();
        System.out.println(amount);
        System.out.println(currency);
    }
}
