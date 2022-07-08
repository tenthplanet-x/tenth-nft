package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class CollectionsExchangeProfileRouteRequest extends AbsRouteRequest<NftExchange.COLLECTION_EXCHANGE_PROFILE_IC, NftExchange.COLLECTION_EXCHANGE_PROFILE_IS> {

    public CollectionsExchangeProfileRouteRequest() {
        super(NftInnerCmds.COLLECTION_EXCHANGE_PROFILE_IC, false, false);
    }
}
