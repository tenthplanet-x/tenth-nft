package com.tenth.nft.marketplace.buildin.controller.routes;

import com.tenth.nft.convention.routes.marketplace.AbsCollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionDetailRouteRequest;
import com.tenth.nft.convention.routes.marketplace.buildin.BuildInCollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.buildin.BuildInCollectionDetailRouteRequest;
import com.tenth.nft.marketplace.buildin.service.BuildInNftCollectionService;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@RestController
@Route
public class BuildInNftCollectionRoutesController {

    @Autowired
    private BuildInNftCollectionService nftCollectionService;

    @RouteRequestMapping(BuildInCollectionDetailRouteRequest.class)
    public NftMarketplace.COLLECTION_DETAIL_IS detail(NftMarketplace.COLLECTION_DETAIL_IC request){
        return nftCollectionService.detail(request);
    }

}
