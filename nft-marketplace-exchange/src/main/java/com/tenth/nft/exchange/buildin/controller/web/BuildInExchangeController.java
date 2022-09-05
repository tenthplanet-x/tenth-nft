package com.tenth.nft.exchange.buildin.controller.web;

import com.tenth.nft.exchange.buildin.ExchangePaths;
import com.tenth.nft.exchange.buildin.controller.vo.*;
import com.tenth.nft.exchange.buildin.service.*;
import com.tenth.nft.exchange.buildin.vo.*;
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
public class BuildInExchangeController {

    @Autowired
    private BuildInListingService nftExchangeService;

    @RequestMapping(ExchangePaths.SELL)
    public Response sell(@RequestBody NftSellRequest request){
        Validations.check(request);
        nftExchangeService.create(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(ExchangePaths.BUY)
    public Response buy(@RequestBody NftBuyRequest request){
        Validations.check(request);
        NftBuyResponse response = nftExchangeService.buy(request);
        return Response.successBuilder().data(response).build();
    }


}
