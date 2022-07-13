package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class SellRouteRequest extends AbsRouteRequest<NftExchange.SELL_IC, NftExchange.SELL_IS> {

    public SellRouteRequest() {
        super(NftInnerCmds.SELL_IC, false, false);
    }
}
