package com.tenth.nft.exchange.web3.controller.routes;

import com.tenth.nft.exchange.web3.service.Web3ListingService;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class Web3ListingRoutesController {

    @Autowired
    private Web3ListingService web3ExchangeService;


//    @RouteRequestMapping(Web3ListingConfirmRouteRequest.class)
//    public NftWeb3Exchange.WEB3_LISTING_CONFIRM_IS confirmListing(NftWeb3Exchange.WEB3_LISTING_CONFIRM_IC request){
//        return web3ExchangeService.confirmListing(request);
//    }
//
//    @RouteRequestMapping(Web3PaymentCreateRouteRequest.class)
//    public NftWeb3Exchange.WEB3_PAYMENT_CREATE_IS createPayment(NftWeb3Exchange.WEB3_PAYMENT_CREATE_IC request){
//        return web3ExchangeService.createPayment(request);
//    }


}
