package com.tenth.nft.marketplace.stats.controller.routes;

import com.tenth.nft.convention.routes.marketplace.stats.ExchangeLogRouteRequest;
import com.tenth.nft.convention.routes.marketplace.stats.CollectionVolumeStatsRouteRequest;
import com.tenth.nft.marketplace.stats.service.NftAssetsExchangeLogService;
import com.tenth.nft.marketplace.stats.service.NftCollectionStatsService;
import com.tenth.nft.marketplace.stats.service.NftExchangeStatsService;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class NftStatsRoutesController {

    @Autowired
    private NftExchangeStatsService nftStatsService;
    @Autowired
    private NftAssetsExchangeLogService nftAssetsExchangeLogService;
    @Autowired
    private NftCollectionStatsService nftCollectionStatsService;

    @RouteRequestMapping(ExchangeLogRouteRequest.class)
    public void exchangeLog(NftMarketplaceStats.EXCHANGE_LOG_IC request){
        nftAssetsExchangeLogService.insert(request);
    }

    @RouteRequestMapping(CollectionVolumeStatsRouteRequest.class)
    public NftMarketplaceStats.COLLECTION_VOLUME_STATS_IS volumeStats(NftMarketplaceStats.COLLECTION_VOLUME_STATS_IC request){
        return nftCollectionStatsService.volumeStats(request);
    }


}
