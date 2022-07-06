package com.tenth.nft.search.route;

import com.tenth.nft.convention.routes.*;
import com.tenth.nft.protobuf.NftSearch;
import com.tenth.nft.search.service.*;
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
    private BlockchainSearchService blockchainSearchService;
    @Autowired
    private CurrencySearchService currenySearchService;
    @Autowired
    private CollectionSearchService collectionSearchService;
    @Autowired
    private AssetsSearchService assetsSearchService;

    @RouteRequestMapping(CategoryRebuildRouteRequest.class)
    public void categoryRebuild(NftSearch.NFT_CATEGORY_REBUILD_IC request){
        categorySearchService.rebuildCache();
    }

    @RouteRequestMapping(BlockchainRebuildRouteRequest.class)
    public void blockchainRebuild(NftSearch.NFT_BLOCKCHAIN_REBUILD_IC request){
        blockchainSearchService.rebuildCache();
    }

    @RouteRequestMapping(CurrencyRebuildRouteRequest.class)
    public void currencyRebuild(NftSearch.NFT_CURRENCY_REBUILD_IC request){
        currenySearchService.rebuildCache(request);
    }

    @RouteRequestMapping(CollectionRebuildRouteRequest.class)
    public void collectionRebuild(NftSearch.NFT_COLLECTION_REBUILD_IC request){
        collectionSearchService.rebuild(request.getCollectionId());
    }

    @RouteRequestMapping(AssetsRebuildRouteRequest.class)
    public void itemsRebuild(NftSearch.NFT_ASSETS_REBUILD_IC request){
        assetsSearchService.rebuild(request.getAssetsId());
    }

}
