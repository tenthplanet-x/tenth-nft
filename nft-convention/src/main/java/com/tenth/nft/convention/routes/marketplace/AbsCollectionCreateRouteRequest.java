package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
public abstract class AbsCollectionCreateRouteRequest extends AbsRouteRequest<NftMarketplace.COLLECTION_CREATE_IC, NftMarketplace.COLLECTION_CREATE_IS> {

    public AbsCollectionCreateRouteRequest(int cmd) {
        super(cmd, false, false);
    }

}
