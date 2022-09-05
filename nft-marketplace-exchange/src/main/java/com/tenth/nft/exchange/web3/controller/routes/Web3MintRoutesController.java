package com.tenth.nft.exchange.web3.controller.routes;

import com.tenth.nft.convention.routes.marketplace.AssetsMintRouteRequest;
import com.tenth.nft.exchange.web3.service.Web3MintService;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class Web3MintRoutesController {

    @Autowired
    private Web3MintService web3AssetsMintService;

    @RouteRequestMapping(AssetsMintRouteRequest.class)
    public void mint(NftMarketplace.ASSETS_MINT_IC request){
        web3AssetsMintService.mint(request);
    }

}
