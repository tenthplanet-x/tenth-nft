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
public class ExchangeEventRouteRequest extends AbsRouteRequest<NftExchange.EXCHANGE_EVENT_IC, Router.AsynNullResponse> {

    public ExchangeEventRouteRequest() {
        super(NftInnerCmds.EXCHANGE_EVENT_IC, true, false);
    }
}
