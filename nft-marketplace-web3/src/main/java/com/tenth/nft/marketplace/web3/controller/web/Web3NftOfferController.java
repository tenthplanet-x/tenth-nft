package com.tenth.nft.marketplace.web3.controller.web;

import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.marketplace.common.dto.NftOfferDTO;
import com.tenth.nft.marketplace.common.vo.*;
import com.tenth.nft.marketplace.web3.Web3NftAssetsPaths;
import com.tenth.nft.marketplace.web3.dto.Web3AcceptCreateSignTicket;
import com.tenth.nft.marketplace.web3.dto.Web3OfferCreateSignTicket;
import com.tenth.nft.marketplace.web3.service.Web3NftAcceptOrderService;
import com.tenth.nft.marketplace.web3.service.Web3NftOfferService;
import com.tenth.nft.marketplace.web3.vo.Web3OfferConfirmRequest;
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
public class Web3NftOfferController {

    @Autowired
    private Web3NftOfferService nftOfferService;
    @Autowired
    private Web3NftAcceptOrderService nftAcceptOrderService;

    @RequestMapping(Web3NftAssetsPaths.OFFER_LIST)
    public Response findOffers(@RequestBody NftOfferListRequest request){
        Validations.check(request);
        Page<NftOfferDTO> dataPage = nftOfferService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(Web3NftAssetsPaths.OFFER_CREATE)
    public Response makeOffer(@RequestBody NftMakeOfferRequest request) throws Exception{
        Validations.check(request);
        Web3OfferCreateSignTicket nftOfferDTO = nftOfferService.makeOffer(request);
        return Response.successBuilder().data(nftOfferDTO).build();
    }

    @RequestMapping(Web3NftAssetsPaths.OFFER_CONFIRM)
    public Response confirmOffer(@RequestBody Web3OfferConfirmRequest request){
        Validations.check(request);
        NftOfferDTO nftOfferDTO = nftOfferService.makeOfferConfirm(request);
        return Response.successBuilder().data(nftOfferDTO).build();
    }

    @RequestMapping(Web3NftAssetsPaths.OFFER_CANCEL)
    public Response cancelOffer(@RequestBody NftOfferCancelRequest request){
        Validations.check(request);
        nftOfferService.cancel(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(Web3NftAssetsPaths.OFFER_ACCEPT)
    public Response acceptOffer(@RequestBody NftOfferAcceptRequest request){
        Validations.check(request);
        Web3AcceptCreateSignTicket signTicket = nftOfferService.accept(request);
        return Response.successBuilder().data(signTicket).build();
    }

    @RequestMapping(Web3NftAssetsPaths.OFFER_ACCEPT_STATUS)
    public Response acceptStatus(@RequestBody NftOrderStatusRequest request){

        TxnStatus status = nftAcceptOrderService.getStatus(request);
        return Response.successBuilder().data(status).build();
    }

}
