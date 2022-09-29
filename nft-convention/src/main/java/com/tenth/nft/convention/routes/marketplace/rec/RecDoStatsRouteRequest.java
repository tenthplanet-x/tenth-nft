package com.tenth.nft.convention.routes.marketplace.rec;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplaceRec;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class RecDoStatsRouteRequest extends AbsRouteRequest<NftMarketplaceRec.REC_DO_STATS_IC, Router.AsynNullResponse> {

    public RecDoStatsRouteRequest() {
        super(NftInnerCmds.REC_DO_STATS_IC, true, false);
    }
}
