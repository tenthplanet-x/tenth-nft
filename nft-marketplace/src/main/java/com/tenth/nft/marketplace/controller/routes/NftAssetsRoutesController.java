package com.tenth.nft.marketplace.controller.routes;

import com.tenth.nft.convention.routes.marketplace.AssetsCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsMintRouteRequest;
import com.tenth.nft.marketplace.service.NftAssetsMintService;
import com.tenth.nft.marketplace.service.NftAssetsService;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@Component
@Route
public class NftAssetsRoutesController {

    @Autowired
    private NftAssetsService nftAssetsService;
    @Autowired
    private NftAssetsMintService nftAssetsMintService;

    @RouteRequestMapping(AssetsCreateRouteRequest.class)
    public NftMarketplace.ASSETS_CREATE_IS create(NftMarketplace.ASSETS_CREATE_IC request){
        return nftAssetsService.create(request);
    }

    @RouteRequestMapping(AssetsDetailRouteRequest.class)
    public NftMarketplace.ASSETS_DETAIL_IS detail(NftMarketplace.ASSETS_DETAIL_IC request){
        return nftAssetsService.detail(request);
    }

    @RouteRequestMapping(AssetsMintRouteRequest.class)
    public void mint(NftMarketplace.ASSETS_MINT_IC request){
        nftAssetsMintService.mint(request);
    }

}
