package com.tenth.nft.exchange.controller.web;

import com.tenth.nft.exchange.ExchangePaths;
import com.tenth.nft.exchange.dto.NftOfferDTO;
import com.tenth.nft.exchange.service.NftOfferService;
import com.tenth.nft.exchange.vo.NftMakeOfferRequest;
import com.tenth.nft.exchange.vo.NftOfferAcceptRequest;
import com.tenth.nft.exchange.vo.NftOfferCancelRequest;
import com.tenth.nft.exchange.vo.NftOfferListRequest;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.convention.dao.dto.Page;
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
public class NftOfferController {

    @Autowired
    private NftOfferService nftOfferService;

    @RequestMapping(ExchangePaths.OFFER_LIST)
    public Response findOffers(@RequestBody NftOfferListRequest request){
        Validations.check(request);
        Page<NftOfferDTO> dataPage = nftOfferService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(ExchangePaths.OFFER_CREATE)
    public Response makeOffer(@RequestBody NftMakeOfferRequest request){
        Validations.check(request);
        NftOfferDTO nftOfferDTO = nftOfferService.makeOffer(request);
        return Response.successBuilder().data(nftOfferDTO).build();
    }

    @RequestMapping(ExchangePaths.OFFER_CANCEL)
    public Response cancelOffer(@RequestBody NftOfferCancelRequest request){
        Validations.check(request);
        nftOfferService.cancel(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(ExchangePaths.OFFER_ACCEPT)
    public Response acceptOffer(@RequestBody NftOfferAcceptRequest request){
        Validations.check(request);
        nftOfferService.accept(request);
        return Response.successBuilder().build();
    }

}
