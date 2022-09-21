package com.tenth.nft.convention.routes.marketplace.buildin;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionCreateRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInCollectionCreateRouteRequest extends AbsCollectionCreateRouteRequest {

    public BuildInCollectionCreateRouteRequest() {
        super(NftInnerCmds.COLLECTION_CREATE_IC);
    }
}
