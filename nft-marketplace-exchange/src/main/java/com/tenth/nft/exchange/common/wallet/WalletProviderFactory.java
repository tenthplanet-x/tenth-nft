package com.tenth.nft.exchange.common.wallet;

import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyConfig;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.exchange.common.wallet.IWalletProvider;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Component
public class WalletProviderFactory {

    @Value("${wallet.blockchain}")
    private String appBlockchain;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    private Map<String, IWalletProvider> providerMap;

    public WalletProviderFactory(IWalletProvider[] providers){
        providerMap = Arrays.stream(providers).collect(Collectors.toMap(
                IWalletProvider::getBlockchain,
                Function.identity()
        ));
    }

    public IWalletProvider get(String blockchain) {
        return providerMap.get(blockchain);
    }

    public IWalletProvider getByCurrency(String currency){

        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        WalletCurrencyConfig walletCurrencyConfig = walletCurrencyTemplate.findOne(currency);
        return get(walletCurrencyConfig.getBlockchain());
    }

    public IWalletProvider getByBlockchain(String blockchain){
        return get(blockchain);
    }
}

