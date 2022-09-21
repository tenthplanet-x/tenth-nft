package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
public abstract class AbsCollectionDetailRouteRequest extends AbsRouteRequest<NftMarketplace.COLLECTION_DETAIL_IC, NftMarketplace.COLLECTION_DETAIL_IS> {

    public AbsCollectionDetailRouteRequest(int cmd) {
        super(cmd, false, false);
    }
}
