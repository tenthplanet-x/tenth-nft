package com.tenth.nft.marketplace.web3.controller.routes;

import com.tenth.nft.convention.routes.marketplace.web3.Web3CollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.web3.Web3CollectionDetailRouteRequest;
import com.tenth.nft.marketplace.web3.service.Web3NftCollectionService;
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
public class Web3NftCollectionRoutesController {

    @Autowired
    private Web3NftCollectionService nftCollectionService;

    @RouteRequestMapping(Web3CollectionDetailRouteRequest.class)
    public NftMarketplace.COLLECTION_DETAIL_IS detail(NftMarketplace.COLLECTION_DETAIL_IC request){
        return nftCollectionService.detail(request);
    }

}
