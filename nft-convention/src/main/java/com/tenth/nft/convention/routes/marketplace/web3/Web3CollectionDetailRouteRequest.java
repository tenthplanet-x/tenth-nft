package com.tenth.nft.convention.routes.marketplace.buildin;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionCreateRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInCollectionDetailRouteRequest extends AbsCollectionCreateRouteRequest {

    public BuildInCollectionDetailRouteRequest() {
        super(NftInnerCmds.COLLECTION_DETAIL_IC);
    }
}
