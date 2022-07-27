package com.tenth.nft.search.web;

import com.tenth.nft.convention.routes.search.CurrencyRatesRouteRequest;
import com.tenth.nft.protobuf.NftSearch;
import com.tenth.nft.search.NftSearchPaths;
import com.tenth.nft.search.dto.CurrencyRateSearchDTO;
import com.tenth.nft.search.dto.CurrencySearchDTO;
import com.tenth.nft.search.service.CurrencySearchService;
import com.tenth.nft.search.vo.CurrenyRateRequest;
import com.tenth.nft.search.vo.CurrenySearchRequest;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shijie
 */
@RestController
@Route
public class CurrenySearchController {

    @Autowired
    private CurrencySearchService currencySearchService;

    @RequestMapping(NftSearchPaths.CURRENCY_LIST)
    public Response list(@RequestBody CurrenySearchRequest request){
        Validations.check(request);
        List<CurrencySearchDTO> currencies = currencySearchService.listByBlockchain(request);
        return Response.successBuilder().data(currencies).build();
    }


    @RequestMapping(NftSearchPaths.CURRENCY_RATE)
    public Response rate(@RequestBody CurrenyRateRequest request){
        Validations.check(request);
        CurrencyRateSearchDTO currencies = currencySearchService.rate(request);
        return Response.successBuilder().data(currencies).build();
    }


    @RouteRequestMapping(CurrencyRatesRouteRequest.class)
    public NftSearch.NFT_CURRENCY_RATES_IS currencyRates(NftSearch.NFT_CURRENCY_RATES_IC request){
        return currencySearchService.currencyRates(request);
    }
}
