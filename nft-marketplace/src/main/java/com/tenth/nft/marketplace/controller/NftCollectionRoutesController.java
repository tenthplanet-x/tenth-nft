package com.tenth.nft.marketplace.controller;

import com.tenth.nft.convention.routes.marketplace.CollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.CollectionDetailRouteRequest;
import com.tenth.nft.marketplace.service.NftCollectionService;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import com.wallan.router.endpoint.core.security.HttpRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 11:07
 */
@RestController
@Route
public class NftCollectionRoutesController {

    @Autowired
    private NftCollectionService nftCollectionService;

    @RouteRequestMapping(CollectionCreateRouteRequest.class)
    public NftMarketplace.COLLECTION_CREATE_IS create(NftMarketplace.COLLECTION_CREATE_IC request){
        return nftCollectionService.create(request);
    }

    @RouteRequestMapping(CollectionDetailRouteRequest.class)
    public NftMarketplace.COLLECTION_DETAIL_IS detail(NftMarketplace.COLLECTION_DETAIL_IC request){
        return nftCollectionService.detail(request);
    }


}
