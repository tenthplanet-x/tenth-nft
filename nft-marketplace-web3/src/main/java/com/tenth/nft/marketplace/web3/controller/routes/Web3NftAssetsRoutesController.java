package com.tenth.nft.marketplace.web3.controller.routes;

import com.tenth.nft.convention.routes.marketplace.web3.Web3AssetsDetailBatchRouteRequest;
import com.tenth.nft.marketplace.web3.service.Web3NftAssetsService;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@Route
public class Web3NftAssetsRoutesController {

    @Autowired
    private Web3NftAssetsService web3NftAssetsService;

    @RouteRequestMapping(Web3AssetsDetailBatchRouteRequest.class)
    public NftMarketplace.ASSETS_DETAIL_BATCH_IS batchDetail(NftMarketplace.ASSETS_DETAIL_BATCH_IC request){
        return web3NftAssetsService.batchDetail(request);
    }

}
