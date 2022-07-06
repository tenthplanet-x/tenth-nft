package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class ActivityListRouteRequest extends AbsRouteRequest<NftExchange.ACTIVITY_LIST_IC, NftExchange.ACTIVITY_LIST_IS> {

    public ActivityListRouteRequest() {
        super(NftInnerCmds.ACTIVITY_LIST_IC, false, false);
    }
}
