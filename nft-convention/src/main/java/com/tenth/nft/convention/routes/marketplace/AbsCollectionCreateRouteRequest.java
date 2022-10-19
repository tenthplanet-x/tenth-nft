package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;

/**
 * @author shijie
 */
public abstract class AbsCollectionCreateRouteRequest extends AbsRouteRequest<NftMarketplace.COLLECTION_CREATE_IC, NftMarketplace.COLLECTION_CREATE_IS> {

    public AbsCollectionCreateRouteRequest(int cmd) {
        super(cmd, false, false);
    }

}
