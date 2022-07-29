package com.tenth.nft.operation.controller.routes;

import com.tenth.nft.convention.routes.operation.BlockchainRouteRequest;
import com.tenth.nft.convention.routes.search.CurrencyRatesRouteRequest;
import com.tenth.nft.operation.service.NftBlockchainService;
import com.tenth.nft.operation.service.NftCurrencyService;
import com.tenth.nft.protobuf.NftOperation;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class NftOpsRoutesController {

    @Autowired
    private NftBlockchainService nftBlockchainService;
    @Autowired
    private NftCurrencyService nftCurrencyService;

    @RouteRequestMapping(BlockchainRouteRequest.class)
    public NftOperation.NFT_BLOCKCHAIN_IS blockchain(NftOperation.NFT_BLOCKCHAIN_IC request){
        return nftBlockchainService.detail(request);
    }

    @RouteRequestMapping(CurrencyRatesRouteRequest.class)
    public NftSearch.NFT_CURRENCY_RATES_IS currencyRates(NftSearch.NFT_CURRENCY_RATES_IC request){
        return nftCurrencyService.currencyRates(request);
    }
}
