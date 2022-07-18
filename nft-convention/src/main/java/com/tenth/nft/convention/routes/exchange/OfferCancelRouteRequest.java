package com.tenth.nft.convention.routes.exchange;

import com.ruixi.tpulse.convention.TpulseInnerCmds;
import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class OfferCancelRouteRequest extends AbsRouteRequest<NftExchange.OFFER_CANCEL_IC, NftExchange.OFFER_CANCEL_IS> {

    public OfferCancelRouteRequest() {
        super(NftInnerCmds.OFFER_CANCEL_IC, false, false);
    }
}
