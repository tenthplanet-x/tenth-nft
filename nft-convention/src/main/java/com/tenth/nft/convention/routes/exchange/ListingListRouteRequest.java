package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class ListingListRouteRequest extends AbsRouteRequest<NftExchange.LISTING_LIST_IC, NftExchange.LISTING_LIST_IS> {

    public ListingListRouteRequest() {
        super(NftInnerCmds.LISTING_LIST_IC, false, false);
    }
}
