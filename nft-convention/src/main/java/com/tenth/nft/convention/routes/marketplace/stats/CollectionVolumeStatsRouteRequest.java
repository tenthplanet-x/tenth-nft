package com.tenth.nft.convention.routes.marketplace.stats;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class CollectionVolumeStatsRouteRequest extends AbsRouteRequest<NftMarketplaceStats.COLLECTION_VOLUME_STATS_IC, NftMarketplaceStats.COLLECTION_VOLUME_STATS_IS> {

    public CollectionVolumeStatsRouteRequest() {
        super(NftInnerCmds.COLLECTION_VOLUME_STATS_IC, false, false);
    }
}
