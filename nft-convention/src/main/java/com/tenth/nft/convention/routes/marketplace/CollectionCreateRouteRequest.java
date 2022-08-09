package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class CollectionCreateRouteRequest extends AbsRouteRequest<NftMarketplace.COLLECTION_CREATE_IC, NftMarketplace.COLLECTION_CREATE_IS> {

    public CollectionCreateRouteRequest() {
        super(NftInnerCmds.COLLECTION_CREATE_IC, false, false);
    }
}
