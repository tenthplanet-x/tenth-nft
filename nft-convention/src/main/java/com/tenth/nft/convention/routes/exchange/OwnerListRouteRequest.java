package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class OwnerListRouteRequest extends AbsRouteRequest<NftExchange.OWNER_LIST_IC, NftExchange.OWNER_LIST_IS> {

    public OwnerListRouteRequest() {
        super(NftInnerCmds.OWNER_LIST_IC, false, false);
    }
}
