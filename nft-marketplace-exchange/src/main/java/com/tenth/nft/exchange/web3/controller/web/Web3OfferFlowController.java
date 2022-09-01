package com.tenth.nft.exchange.web3.controller.web;

import com.tenth.nft.exchange.buildin.dto.NftOfferDTO;
import com.tenth.nft.exchange.web3.Web3ExchangePaths;
import com.tenth.nft.exchange.web3.service.Web3OfferFlowService;
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
public class Web3OfferFlowController {

    @Autowired
    private Web3OfferFlowService web3OfferFlowService;

    @RequestMapping(Web3ExchangePaths.OFFER_CREATE)
    public Response createOffer(@RequestBody Web3OfferCreateRequest request){
        Validations.check(request);
        Web3OfferCreateResponse response = web3OfferFlowService.createOffer(request);
        return Response.successBuilder().data(response).build();
    }

    @RequestMapping(Web3ExchangePaths.OFFER_CONFIRM)
    public Response confirmOffer(@RequestBody Web3OfferConfirmRequest request){
        Validations.check(request);
        NftOfferDTO response = web3OfferFlowService.confirmOffer(request);
        return Response.successBuilder().data(response).build();
    }

    @RequestMapping(Web3ExchangePaths.ACCEPT_CREATE)
    public Response createAccept(@RequestBody Web3AcceptCreateRequest request){
        Validations.check(request);
        Web3AcceptCreateResponse response = web3OfferFlowService.createAccept(request);
        return Response.successBuilder().data(response).build();

    }


}
