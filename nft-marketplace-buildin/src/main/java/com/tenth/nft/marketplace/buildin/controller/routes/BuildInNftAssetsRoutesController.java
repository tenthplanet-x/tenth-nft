package com.tenth.nft.marketplace.buildin.controller.routes;

import com.tenth.nft.convention.routes.marketplace.buildin.BuildInAssetsDetailBatchRouteRequest;
import com.tenth.nft.marketplace.buildin.service.BuildInNftAssetsService;
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
public class BuildInNftAssetsRoutesController {

    @Autowired
    private BuildInNftAssetsService buildInNftAssetsService;

    @RouteRequestMapping(BuildInAssetsDetailBatchRouteRequest.class)
    public NftMarketplace.ASSETS_DETAIL_BATCH_IS batchDetail(NftMarketplace.ASSETS_DETAIL_BATCH_IC request){
        return buildInNftAssetsService.batchDetail(request);
    }

}
