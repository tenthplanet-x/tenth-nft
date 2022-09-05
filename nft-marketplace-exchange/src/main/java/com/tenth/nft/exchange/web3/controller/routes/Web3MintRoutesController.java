package com.tenth.nft.exchange.web3.controller.routes;

import com.tenth.nft.convention.routes.marketplace.AssetsMintRouteRequest;
import com.tenth.nft.exchange.web3.Web3ExchangePaths;
import com.tenth.nft.exchange.web3.service.Web3AssetsMintService;
import com.tenth.nft.exchange.web3.vo.NftAssetsMintCheckRequest;
import com.tenth.nft.exchange.web3.vo.NftAssetsMintCheckResponse;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.commons.validation.Validations;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@Component
@Route
public class Web3MintRoutesController {

    @Autowired
    private Web3AssetsMintService web3AssetsMintService;

    @RouteRequestMapping(AssetsMintRouteRequest.class)
    public void mint(NftMarketplace.ASSETS_MINT_IC request){
        web3AssetsMintService.mint(request);
    }

}
