package com.tenth.nft.marketplace.web3.controller.routes;

import com.tenth.nft.convention.routes.marketplace.web3.Web3BuyReceiptRouteRequest;
import com.tenth.nft.marketplace.web3.service.Web3NftListingService;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class Web3NftListingRoutesController {

    @Autowired
    private Web3NftListingService web3NftListingService;

    @RouteRequestMapping(Web3BuyReceiptRouteRequest.class)
    public NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS confirmPayment(NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IC request){

        NftExchange.PAYMENT_RECEIVE_IS res = web3NftListingService.receivePayment(request);

        return NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS.newBuilder().build();
    }

}
