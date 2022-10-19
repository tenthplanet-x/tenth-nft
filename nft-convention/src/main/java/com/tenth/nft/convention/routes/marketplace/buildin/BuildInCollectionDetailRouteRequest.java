package com.tenth.nft.convention.routes.marketplace.buildin;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionDetailRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInCollectionDetailRouteRequest extends AbsCollectionDetailRouteRequest {

    public BuildInCollectionDetailRouteRequest() {
        super(NftInnerCmds.BUILDIN_COLLECTION_DETAIL_IC);
    }
}
