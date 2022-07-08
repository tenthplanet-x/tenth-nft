package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class AssetsExchangeProfileRouteRequest extends AbsRouteRequest<NftExchange.ASSETS_EXCHANGE_PROFILE_IC, NftExchange.ASSETS_EXCHANGE_PROFILE_IS> {

    public AssetsExchangeProfileRouteRequest() {
        super(NftInnerCmds.ASSETS_EXCHANGE_PROFILE_IC, false, false);
    }
}
