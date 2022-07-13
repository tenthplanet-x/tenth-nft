package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class MintRouteRequest extends AbsRouteRequest<NftExchange.MINT_IC, NftExchange.MINT_IS> {

    public MintRouteRequest() {
        super(NftInnerCmds.MINT_IC, false, false);
    }
}
