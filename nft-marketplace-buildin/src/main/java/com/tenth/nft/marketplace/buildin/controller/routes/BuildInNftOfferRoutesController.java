package com.tenth.nft.marketplace.buildin.controller.routes;

import com.tenth.nft.convention.routes.marketplace.buildin.BuildInAcceptReceiptRouteRequest;
import com.tenth.nft.marketplace.buildin.service.BuildInNftOfferService;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@Route
public class BuildInNftOfferRoutesController {

    @Autowired
    private BuildInNftOfferService buildInNftOfferService;

    @RouteRequestMapping(BuildInAcceptReceiptRouteRequest.class)
    public NftExchange.PAYMENT_RECEIVE_IS acceptBuyReceipt(NftExchange.PAYMENT_RECEIVE_IC request){
        return buildInNftOfferService.receiveReceipt(request);
    }

}
