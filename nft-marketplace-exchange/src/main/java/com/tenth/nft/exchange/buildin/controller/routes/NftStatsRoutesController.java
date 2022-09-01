package com.tenth.nft.exchange.buildin.controller.routes;

import com.tenth.nft.convention.routes.exchange.ExchangeEventRouteRequest;
import com.tenth.nft.convention.routes.exchange.ListingEventRouteRequest;
import com.tenth.nft.exchange.buildin.service.NftStatsService;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class NftStatsRoutesController {

    @Autowired
    private NftStatsService nftStatsService;

    /**
     *
     */
    @RouteRequestMapping(ListingEventRouteRequest.class)
    public void listingEventHandle(NftExchange.LISTING_EVENT_IC request){
        nftStatsService.listingEventHandle(request);
    }

    /**
     *
     */
    @RouteRequestMapping(ExchangeEventRouteRequest.class)
    public void exchangeEventHandle(NftExchange.EXCHANGE_EVENT_IC request){
        nftStatsService.exchangeEventHandle(request);
    }



}
