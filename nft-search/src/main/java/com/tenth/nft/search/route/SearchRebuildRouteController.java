package com.tenth.nft.search.route;

import com.tenth.nft.convention.routes.AssetsRebuildRouteRequest;
import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.protobuf.NftSearch;
import com.tenth.nft.search.service.AssetsSearchService;
import com.tenth.nft.search.service.CollectionSearchService;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * listeners of events of rebuild
 * @author shijie
 */
@Component
@Route
public class SearchRebuildRouteController {

    @Autowired
    private CollectionSearchService collectionSearchService;
    @Autowired
    private AssetsSearchService assetsSearchService;

    @RouteRequestMapping(CollectionRebuildRouteRequest.class)
    public void collectionRebuild(NftSearch.NFT_COLLECTION_REBUILD_IC request){
        collectionSearchService.rebuild(request.getCollectionId());
    }

    @RouteRequestMapping(AssetsRebuildRouteRequest.class)
    public void itemsRebuild(NftSearch.NFT_ASSETS_REBUILD_IC request){
        assetsSearchService.rebuild(request.getAssetsId());
    }


}
