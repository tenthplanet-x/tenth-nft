package com.tenth.nft.convention.routes.marketplace.stats;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class ExchangeLogRouteRequest extends AbsRouteRequest<NftMarketplaceStats.EXCHANGE_LOG_IC, Router.AsynNullResponse> {

    public ExchangeLogRouteRequest() {
        super(NftInnerCmds.EXCHANGE_LOG_IC, true, false);
    }
}
