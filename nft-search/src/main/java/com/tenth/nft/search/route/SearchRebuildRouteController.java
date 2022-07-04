package com.tenth.nft.search.route;

import com.tenth.nft.convention.routes.ExternalCategoryRebuildRouteRequest;
import com.tenth.nft.convention.routes.ExternalCollectionRebuildRouteRequest;
import com.tenth.nft.convention.routes.ExternalItemsRebuildRouteRequest;
import com.tenth.nft.protobuf.NftSearch;
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

    @RouteRequestMapping(ExternalCategoryRebuildRouteRequest.class)
    public void categoryRebuild(NftSearch.EXTERNAL_NFT_CATEGORY_REBUILD_IC request){
        categorySearchService.rebuildCache();
    }

    @RouteRequestMapping(ExternalCollectionRebuildRouteRequest.class)
    public void collectionRebuild(NftSearch.EXTERNAL_NFT_COLLECTION_REBUILD_IC request){
        collectionSearchService.rebuild(request.getCollectionId());
    }

    @RouteRequestMapping(ExternalItemsRebuildRouteRequest.class)
    public void itemsRebuild(NftSearch.EXTERNAL_NFT_ITEM_REBUILD_IC request){
        collectionSearchService.rebuildItems(request.getCollectionId());
    }

}
