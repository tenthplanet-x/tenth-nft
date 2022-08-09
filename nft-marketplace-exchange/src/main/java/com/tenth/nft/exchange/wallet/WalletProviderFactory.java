package com.tenth.nft.exchange.wallet;

import com.tenth.nft.convention.routes.operation.NftCurrencyRouteRequest;
import com.tenth.nft.convention.routes.search.CurrencyRatesRouteRequest;
import com.tenth.nft.protobuf.NftOperation;
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

        NftOperation.CurrencyDTO currencyDTO = routeClient.send(
                NftOperation.NFT_CURRENCY_IC.newBuilder()
                        .setCurrency(currency)
                        .build(),
                NftCurrencyRouteRequest.class
        ).getCurrency();
        return get(currencyDTO.getBlockchain());
    }
}

