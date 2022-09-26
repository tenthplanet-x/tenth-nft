package com.tenth.nft.marketplace.buildin.controller.routes;

import com.tenth.nft.convention.routes.marketplace.buildin.BuildInBuyReceiptRouteRequest;
import com.tenth.nft.marketplace.buildin.service.BuildInNftListingService;
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
public class BuildInNftListingRoutesController {

    @Autowired
    private BuildInNftListingService buildInNftListingService;

    @RouteRequestMapping(BuildInBuyReceiptRouteRequest.class)
    public NftExchange.PAYMENT_RECEIVE_IS acceptBuyReceipt(NftExchange.PAYMENT_RECEIVE_IC request){
        return buildInNftListingService.receiveReceipt(request);
    }

}
