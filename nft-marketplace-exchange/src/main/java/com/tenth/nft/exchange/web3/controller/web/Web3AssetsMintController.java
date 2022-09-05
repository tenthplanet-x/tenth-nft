package com.tenth.nft.exchange.web3.controller.web;

import com.tenth.nft.exchange.web3.Web3ExchangePaths;
import com.tenth.nft.exchange.web3.service.Web3AssetsMintService;
import com.tenth.nft.exchange.web3.vo.NftAssetsMintCheckRequest;
import com.tenth.nft.exchange.web3.vo.NftAssetsMintCheckResponse;
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
public class Web3AssetsMintController {

    @Autowired
    private Web3AssetsMintService web3AssetsMintService;

    @RequestMapping(Web3ExchangePaths.NFT_ASSETS_MINT_CHECK)
    public Response checkMinting(@RequestBody NftAssetsMintCheckRequest request){
        Validations.check(request);
        NftAssetsMintCheckResponse response = web3AssetsMintService.checkMinting(request);
        return Response.successBuilder().data(response).build();
    }

}
