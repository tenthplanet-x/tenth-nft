package com.tenth.nft.exchange.web3.controller.web;

import com.tenth.nft.exchange.web3.Web3ExchangePaths;
import com.tenth.nft.exchange.web3.dto.ListingCreateResponse;
import com.tenth.nft.exchange.web3.dto.PaymentCheckResponse;
import com.tenth.nft.exchange.web3.dto.PaymentCreateResponse;
import com.tenth.nft.exchange.web3.service.Web3ExchangeService;
import com.tenth.nft.exchange.web3.vo.*;
import com.tpulse.commons.validation.Validations;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@HttpRoute(userAuth = true)
public class Web3ExchangeController {

    @Autowired
    private Web3ExchangeService web3ExchangeService;

//    @RequestMapping(Web3ExchangePaths.MINT)
//    public Response mint(Web3ExchangeMintRequest request){
//        web3ExchangeService.mint(request);
//    }

    @RequestMapping(Web3ExchangePaths.LISTING_CREATE)
    public Response createListing(@RequestBody Web3ExchangeListingAuthRequest request){
        Validations.check(request);
        ListingCreateResponse result = web3ExchangeService.createListing(request);
        return Response.successBuilder().data(result).build();
    }

    @RequestMapping(Web3ExchangePaths.LISTING_CONFIRM)
    public Response confirmListing(Web3ExchangeListingConfirmRequest request){
        web3ExchangeService.confirmListing(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(Web3ExchangePaths.PAYMENT_CREATE)
    public Response createPayment(Web3PaymentCreateRequest request){
        PaymentCreateResponse result = web3ExchangeService.createPayment(request);
        return Response.successBuilder().data(result).build();
    }

    @RequestMapping(Web3ExchangePaths.PAYMENT_CONFIRM)
    public Response confirmPayment(Web3PaymentConfirmRequest request){
        web3ExchangeService.confirmPayment(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(Web3ExchangePaths.PAYMENT_CHECK)
    public Response checkPayment(Web3PaymentCheckRequest request){
        PaymentCheckResponse response = web3ExchangeService.getPaymentState(request);
        return Response.successBuilder().data(response).build();
    }


}
