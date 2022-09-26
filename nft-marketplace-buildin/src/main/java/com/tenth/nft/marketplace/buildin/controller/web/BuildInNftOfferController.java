package com.tenth.nft.marketplace.buildin.controller.web;

import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.marketplace.buildin.BuildInNftAssetsPaths;
import com.tenth.nft.marketplace.buildin.dto.BuildInNftOfferDTO;
import com.tenth.nft.marketplace.buildin.service.BuildInNftAcceptOrderService;
import com.tenth.nft.marketplace.buildin.service.BuildInNftOfferService;
import com.tenth.nft.marketplace.common.dto.NftOfferDTO;
import com.tenth.nft.marketplace.common.vo.*;
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
public class BuildInNftOfferController {

    @Autowired
    private BuildInNftOfferService nftOfferService;
    @Autowired
    private BuildInNftAcceptOrderService nftAcceptOrderService;

    @RequestMapping(BuildInNftAssetsPaths.OFFER_LIST)
    public Response findOffers(@RequestBody NftOfferListRequest request){
        Validations.check(request);
        Page<BuildInNftOfferDTO> dataPage = nftOfferService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(BuildInNftAssetsPaths.OFFER_CREATE)
    public Response makeOffer(@RequestBody NftMakeOfferRequest request){
        Validations.check(request);
        NftOfferDTO nftOfferDTO = nftOfferService.makeOffer(request);
        return Response.successBuilder().data(nftOfferDTO).build();
    }

    @RequestMapping(BuildInNftAssetsPaths.OFFER_CANCEL)
    public Response cancelOffer(@RequestBody NftOfferCancelRequest request){
        Validations.check(request);
        nftOfferService.cancel(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(BuildInNftAssetsPaths.OFFER_ACCEPT)
    public Response acceptOffer(@RequestBody NftOfferAcceptRequest request){
        Validations.check(request);
        Long outOrderId = nftOfferService.accept(request);
        return Response.successBuilder().data(outOrderId).build();
    }

    @RequestMapping(BuildInNftAssetsPaths.OFFER_ACCEPT_STATUS)
    public Response acceptStatus(@RequestBody NftOrderStatusRequest request){
        TxnStatus status = nftAcceptOrderService.getStatus(request);
        return Response.successBuilder().data(status).build();
    }

}
