package com.tenth.nft.convention.routes.marketplace.web3;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionCreateRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionDetailRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3CollectionDetailRouteRequest extends AbsCollectionDetailRouteRequest {

    public Web3CollectionDetailRouteRequest() {
        super(NftInnerCmds.WEB3_COLLECTION_DETAIL_IC);
    }
}
