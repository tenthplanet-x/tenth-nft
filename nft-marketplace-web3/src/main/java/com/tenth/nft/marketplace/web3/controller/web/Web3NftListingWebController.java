package com.tenth.nft.marketplace.web3.controller.web;

import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;
import com.tenth.nft.marketplace.common.vo.*;
import com.tenth.nft.marketplace.web3.Web3NftAssetsPaths;
import com.tenth.nft.marketplace.web3.dto.Web3SendTransactionTicket;
import com.tenth.nft.marketplace.web3.dto.WebListingSignTicket;
import com.tenth.nft.marketplace.web3.service.Web3NftBuyOrderService;
import com.tenth.nft.marketplace.web3.service.Web3NftListingService;
import com.tenth.nft.marketplace.web3.vo.Web3NftAssetsCreateConfirmRequest;
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
public class Web3NftListingWebController {

    @Autowired
    private Web3NftListingService nftListingService;
    @Autowired
    private Web3NftBuyOrderService nftBuyOrderService;

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_CREATE)
    public Response create(@RequestBody NftListingCreateRequest request) throws Exception{
        Validations.check(request);
        return Response.successBuilder().data(nftListingService.create(request)).build();
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_CREATE_CONFIRM)
    public Response createConfirm(@RequestBody Web3NftAssetsCreateConfirmRequest request){
        Validations.check(request);
        return Response.successBuilder().data(nftListingService.createConfirm(request)).build();
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_CANCEL)
    public Response cancel(@RequestBody NftListingCancelRequest request){
        Validations.check(request);
        nftListingService.cancel(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_LIST)
    public Response list(@RequestBody NftListingListRequest request){
        Validations.check(request);
        return Response.successBuilder().data(nftListingService.list(request)).build();
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_BUY)
    public Response buy(@RequestBody NftListingBuyRequest request){
        Validations.check(request);
        return Response.successBuilder().data(nftListingService.buy(request)).build();
    }

    @RequestMapping(Web3NftAssetsPaths.NFT_LISTING_BUY_STATUS)
    public Response status(@RequestBody NftOrderStatusRequest request){
        Validations.check(request);
        TxnStatus txnStatus = nftBuyOrderService.getStatus(request);
        return Response.successBuilder().data(txnStatus).build();
    }

}
