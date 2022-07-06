package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuyRouteRequest extends AbsRouteRequest<NftExchange.BUY_IC, NftExchange.BUY_IS> {

    public BuyRouteRequest() {
        super(NftInnerCmds.BUY_IC, false, false);
    }
}
