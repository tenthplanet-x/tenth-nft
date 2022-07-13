package com.tenth.nft.exchange.controller.web;

import com.tenth.nft.exchange.ExchangePaths;
import com.tenth.nft.exchange.service.*;
import com.tenth.nft.exchange.controller.vo.*;
import com.tenth.nft.exchange.dto.NftActivityDTO;
import com.tenth.nft.exchange.dto.NftListingDTO;
import com.tenth.nft.exchange.dto.NftMintDTO;
import com.tenth.nft.exchange.dto.NftOwnerDTO;
import com.tenth.nft.exchange.vo.NftSellRequest;
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
public class NftExchangeController {

    @Autowired
    private NftMintService nftMintService;
    @Autowired
    private NftListingService nftListingService;
    @Autowired
    private NftActivityService nftActivityService;
    @Autowired
    private NftOwnerService nftOwnerService;
    @Autowired
    private NftExchangeService nftExchangeService;

    @RequestMapping(ExchangePaths.SELL)
    public Response sell(@RequestBody NftSellRequest request){
        Validations.check(request);
        nftExchangeService.sell(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(ExchangePaths.BUY)
    public Response buy(@RequestBody NftBuyRequest request){
        Validations.check(request);
        nftExchangeService.buy(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(ExchangePaths.OWNER_LIST)
    public Response owner(@RequestBody NftOwnerListRequest request){
        Validations.check(request);
        Page<NftOwnerDTO> dataPage = nftOwnerService.list(request);
        return Response.successBuilder().data(dataPage).build();
    }

    @RequestMapping(ExchangePaths.MINT)
    public Response mint(@RequestBody NftMintRequest request){
        Validations.check(request);
        NftMintDTO profile = nftMintService.detail(request);
        return Response.successBuilder().data(profile).build();
    }

    @RequestMapping(ExchangePaths.LISTING_LIST)
    public Response listings(@RequestBody NftListingListRequest request){
        Validations.check(request);
        Page<NftListingDTO> profile = nftListingService.list(request);
        return Response.successBuilder().data(profile).build();
    }

    @RequestMapping(ExchangePaths.ACTIVITY_LIST)
    public Response activityList(@RequestBody NftActivityListRequest request){
        Validations.check(request);
        Page<NftActivityDTO> profile = nftActivityService.list(request);
        return Response.successBuilder().data(profile).build();
    }


}
