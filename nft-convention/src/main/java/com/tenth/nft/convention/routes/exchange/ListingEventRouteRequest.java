package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class ListingEventRouteRequest extends AbsRouteRequest<NftExchange.LISTING_EVENT_IC, Router.AsynNullResponse> {

    public ListingEventRouteRequest() {
        super(NftInnerCmds.LISTING_EVENT_IC, true, false);
    }
}
