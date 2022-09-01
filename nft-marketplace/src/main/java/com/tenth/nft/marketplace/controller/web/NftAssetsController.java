package com.tenth.nft.marketplace.controller.web;

import com.tenth.nft.marketplace.NftAssetsPaths;
import com.tenth.nft.marketplace.service.NftAssetsMintService;
import com.tenth.nft.marketplace.vo.NftAssetsMintCheckRequest;
import com.tenth.nft.marketplace.vo.NftAssetsMintCheckResponse;
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
public class NftAssetsController {

    @Autowired
    private NftAssetsMintService nftAssetsMintService;

    @RequestMapping(NftAssetsPaths.NFT_ASSETS_MINT_CHECK)
    public Response checkMinting(@RequestBody NftAssetsMintCheckRequest request){
        Validations.check(request);
        NftAssetsMintCheckResponse response = nftAssetsMintService.checkMinting(request);
        return Response.successBuilder().data(response).build();
    }


}
