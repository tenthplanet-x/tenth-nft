package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class SellCancelRouteRequest extends AbsRouteRequest<NftExchange.SELL_CANCEL_IC, NftExchange.SELL_CANCEL_IS> {

    public SellCancelRouteRequest() {
        super(NftInnerCmds.SELL_CANCEL_IC, false, false);
    }
}
