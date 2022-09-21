package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class CollectionDetailRouteRequest extends AbsRouteRequest<NftMarketplace.COLLECTION_DETAIL_IC, NftMarketplace.COLLECTION_DETAIL_IS> {

    public CollectionDetailRouteRequest() {
        super(NftInnerCmds.COLLECTION_DETAIL_IC, false, false);
    }
}
