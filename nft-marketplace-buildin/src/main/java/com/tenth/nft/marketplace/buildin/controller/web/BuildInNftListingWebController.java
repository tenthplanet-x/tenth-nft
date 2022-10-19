package com.tenth.nft.marketplace.buildin.controller.web;

import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.marketplace.buildin.BuildInNftAssetsPaths;
import com.tenth.nft.marketplace.buildin.dto.BuildInNftListingDTO;
import com.tenth.nft.marketplace.buildin.service.BuildInNftBuyOrderService;
import com.tenth.nft.marketplace.buildin.service.BuildInNftListingService;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;
import com.tenth.nft.marketplace.common.dto.NftWalletPayTicket;
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
public class BuildInNftListingWebController {

    @Autowired
    private BuildInNftListingService buildInNftListingService;
    @Autowired
    private BuildInNftBuyOrderService nftBuyOrderService;

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_CREATE)
    public Response create(@RequestBody NftListingCreateRequest request){
        Validations.check(request);
        return Response.successBuilder().data(buildInNftListingService.create(request)).build();
    }

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_CANCEL)
    public Response cancel(@RequestBody NftListingCancelRequest request){
        Validations.check(request);
        buildInNftListingService.cancel(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_LIST)
    public Response list(@RequestBody NftListingListRequest request){
        Validations.check(request);
        return Response.successBuilder().data(buildInNftListingService.list(request)).build();
    }

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_BUY)
    public Response buy(@RequestBody NftListingBuyRequest request){
        Validations.check(request);
        return Response.successBuilder().data(buildInNftListingService.buy(request)).build();
    }

    @RequestMapping(BuildInNftAssetsPaths.NFT_LISTING_BUY_STATUS)
    public Response status(@RequestBody NftOrderStatusRequest request){
        Validations.check(request);
        TxnStatus txnStatus = nftBuyOrderService.getStatus(request);
        return Response.successBuilder().data(txnStatus).build();
    }

}
