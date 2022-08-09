package com.tenth.nft.operation.controller.web;

import com.tenth.nft.convention.routes.search.CurrencyRatesRouteRequest;
import com.tenth.nft.operation.OpsSearchPaths;
import com.tenth.nft.operation.dto.CurrencyRateSearchDTO;
import com.tenth.nft.operation.dto.CurrencySearchDTO;
import com.tenth.nft.operation.service.CurrencyRateService;
import com.tenth.nft.operation.service.NftCurrencyService;
import com.tenth.nft.operation.vo.CurrenyRateRequest;
import com.tenth.nft.operation.vo.CurrenySearchRequest;
import com.tenth.nft.protobuf.NftSearch;
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
    private NftCurrencyService currencySearchService;
    @Autowired
    private CurrencyRateService currencyRates;

    @RequestMapping(OpsSearchPaths.CURRENCY_LIST)
    public Response list(@RequestBody CurrenySearchRequest request){
        Validations.check(request);
        List<CurrencySearchDTO> currencies = currencySearchService.listByBlockchain(request);
        return Response.successBuilder().data(currencies).build();
    }


    @RequestMapping(OpsSearchPaths.CURRENCY_RATE)
    public Response rate(@RequestBody CurrenyRateRequest request){
        Validations.check(request);
        CurrencyRateSearchDTO currencies = currencyRates.rate(request);
        return Response.successBuilder().data(currencies).build();
    }



}
