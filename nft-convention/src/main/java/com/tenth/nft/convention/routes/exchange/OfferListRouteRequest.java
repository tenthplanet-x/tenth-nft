package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class OfferListRouteRequest extends AbsRouteRequest<NftExchange.OFFER_LIST_IC, NftExchange.OFFER_LIST_IS> {

    public OfferListRouteRequest() {
        super(NftInnerCmds.OFFER_LIST_IC, false, false);
    }
}
