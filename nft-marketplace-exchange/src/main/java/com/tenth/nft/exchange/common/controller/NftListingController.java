package com.tenth.nft.exchange.common.controller;

import com.tenth.nft.exchange.buildin.ExchangePaths;
import com.tenth.nft.exchange.buildin.controller.vo.NftListingListRequest;
import com.tenth.nft.exchange.buildin.dto.NftListingDTO;
import com.tenth.nft.exchange.buildin.vo.SellCancelRequest;
import com.tenth.nft.exchange.common.service.NftListingService;
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
public class NftListingController {

    @Autowired
    private NftListingService nftListingService;

    @RequestMapping(ExchangePaths.LISTING_LIST)
    public Response listings(@RequestBody NftListingListRequest request){
        Validations.check(request);
        Page<NftListingDTO> profile = nftListingService.list(request);
        return Response.successBuilder().data(profile).build();
    }

    @RequestMapping(ExchangePaths.SELL_CANCEL)
    public Response sellCancel(@RequestBody SellCancelRequest request){
        Validations.check(request);
        nftListingService.cancel(request.getAssetsId(), request.getListingId(), "");
        return Response.successBuilder().build();
    }

}
