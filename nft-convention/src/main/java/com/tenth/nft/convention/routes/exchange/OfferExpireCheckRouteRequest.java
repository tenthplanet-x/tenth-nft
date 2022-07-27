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
public class OfferExpireCheckRouteRequest extends AbsRouteRequest<NftExchange.OFFER_EXPIRE_CHECK_IC, Router.AsynNullResponse> {

    public OfferExpireCheckRouteRequest() {
        super(NftInnerCmds.OFFER_EXPIRE_CHECK_IC, true, false);
    }
}
