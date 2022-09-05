package com.tenth.nft.exchange.web3.controller.routes;

import com.tenth.nft.convention.routes.exchange.Web3PaymentConfirmRouteRequest;
import com.tenth.nft.exchange.web3.service.Web3OrderService;
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
public class Web3OrderRoutesController {

    @Autowired
    private Web3OrderService web3OrderService;

    @RouteRequestMapping(Web3PaymentConfirmRouteRequest.class)
    public NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS confirmPayment(NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IC request){
        return web3OrderService.confirmPayment(request);
    }

}
