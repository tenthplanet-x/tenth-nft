package com.tenth.nft.exchange.buildin.controller.routes;

import com.tenth.nft.convention.routes.exchange.*;
import com.tenth.nft.exchange.buildin.service.BuildInOfferService;
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
    private BuildInOfferService nftOfferService;

    @RouteRequestMapping(OfferExpireCheckRouteRequest.class)
    public void expireCheck(NftExchange.OFFER_EXPIRE_CHECK_IC request){
        nftOfferService.expireCheck(request);
    }

}
