package com.tenth.nft.template;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.convention.templates.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class WalletCurrencyTemplateTest {


    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    @Test
    public void test(){

        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        List<WalletCurrencyConfig> currencies = walletCurrencyTemplate.findByBlockchain("T Planet");
        Assert.assertTrue(!currencies.isEmpty());
    }

    @Test
    public void mainCurrency(){
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        WalletCurrencyConfig walletCurrencyConfig = walletCurrencyTemplate.findMainCurrency("T Planet");
        Assert.assertTrue(null != walletCurrencyConfig);
    }

}
