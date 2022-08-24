package com.tenth.nft.exchange.buildin.controller.routes;

import com.tenth.nft.convention.routes.exchange.*;
import com.tenth.nft.exchange.buildin.service.NftOfferService;
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
public class NftOfferRoutesController {

    @Autowired
    private NftOfferService nftOfferService;

    @RouteRequestMapping(OfferListRouteRequest.class)
    public NftExchange.OFFER_LIST_IS list(NftExchange.OFFER_LIST_IC request){
        return nftOfferService.offerList(request);
    }

    @RouteRequestMapping(OfferMakeRouteRequest.class)
    public NftExchange.OFFER_MAKE_IS make(NftExchange.OFFER_MAKE_IC request){
        return nftOfferService.makeOffer(request);
    }

    @RouteRequestMapping(OfferCancelRouteRequest.class)
    public NftExchange.OFFER_CANCEL_IS cancel(NftExchange.OFFER_CANCEL_IC request){
        nftOfferService.cancel(request);
        return NftExchange.OFFER_CANCEL_IS.newBuilder().build();
    }

    @RouteRequestMapping(OfferAcceptRouteRequest.class)
    public NftExchange.OFFER_ACCEPT_IS accept(NftExchange.OFFER_ACCEPT_IC request){
        nftOfferService.accept(request);
        return NftExchange.OFFER_ACCEPT_IS.newBuilder().build();
    }

    @RouteRequestMapping(OfferExpireCheckRouteRequest.class)
    public void expireCheck(NftExchange.OFFER_EXPIRE_CHECK_IC request){
        nftOfferService.expireCheck(request);
    }

}
