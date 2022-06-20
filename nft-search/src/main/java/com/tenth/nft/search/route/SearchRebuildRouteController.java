package com.tenth.nft.search.route;

import com.tenth.nft.convention.routes.CategoryRebuildRouteRequest;
import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.convention.routes.ItemsRebuildRouteRequest;
import com.tenth.nft.protobuf.Search;
import com.tenth.nft.search.service.CategorySearchService;
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
    private CategorySearchService categorySearchService;
    @Autowired
    private CollectionSearchService collectionSearchService;

    @RouteRequestMapping(CategoryRebuildRouteRequest.class)
    public void categoryRebuild(Search.NFT_CATEGORY_REBUILD_IC request){
        categorySearchService.rebuildCache();
    }

    @RouteRequestMapping(CollectionRebuildRouteRequest.class)
    public void collectionRebuild(Search.NFT_COLLECTION_REBUILD_IC request){
        collectionSearchService.rebuild(request.getCollectionId());
    }

    @RouteRequestMapping(ItemsRebuildRouteRequest.class)
    public void itemsRebuild(Search.NFT_ITEM_REBUILD_IC request){
        collectionSearchService.rebuildItems(request.getCollectionId());
    }

}
