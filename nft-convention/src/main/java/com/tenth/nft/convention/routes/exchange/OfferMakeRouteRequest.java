package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class OfferMakeRouteRequest extends AbsRouteRequest<NftExchange.OFFER_MAKE_IC, NftExchange.OFFER_MAKE_IS> {

    public OfferMakeRouteRequest() {
        super(NftInnerCmds.OFFER_MAKE_IC, false, false);
    }


}
