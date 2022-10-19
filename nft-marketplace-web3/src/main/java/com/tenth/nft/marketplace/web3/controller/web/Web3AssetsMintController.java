package com.tenth.nft.marketplace.web3.controller.web;

import com.tenth.nft.marketplace.web3.Web3NftAssetsPaths;
import com.tenth.nft.marketplace.web3.dto.NftAssetsMintCheckResponse;
import com.tenth.nft.marketplace.web3.service.Web3NftMintService;
import com.tenth.nft.marketplace.web3.vo.NftAssetsMintCheckRequest;
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
    private Web3NftMintService web3AssetsMintService;

    @RequestMapping(Web3NftAssetsPaths.NFT_ASSETS_MINT_CHECK)
    public Response checkMinting(@RequestBody NftAssetsMintCheckRequest request){
        Validations.check(request);
        NftAssetsMintCheckResponse response = web3AssetsMintService.checkMinting(request);
        return Response.successBuilder().data(response).build();
    }

}
