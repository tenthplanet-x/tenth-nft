package com.tenth.nft.convention.routes.marketplace.buildin;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionCreateRouteRequest;

/**
 * @author shijie
 */
public class BuildInCollectionCreateRouteRequest extends AbsCollectionCreateRouteRequest {

    public BuildInCollectionCreateRouteRequest() {
        super(NftInnerCmds.COLLECTION_CREATE_IC);
    }
}
