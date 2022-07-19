package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class OfferAcceptRouteRequest extends AbsRouteRequest<NftExchange.OFFER_ACCEPT_IC, NftExchange.OFFER_ACCEPT_IS> {

    public OfferAcceptRouteRequest() {
        super(NftInnerCmds.OFFER_ACCEPT_IC, false, false);
    }
}
