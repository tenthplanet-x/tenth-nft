package com.tenth.nft.convention.routes.marketplace.web3;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.cmd.NftWeb3Cmds;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionCreateRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3CollectionDetailRouteRequest extends AbsCollectionCreateRouteRequest {

    public Web3CollectionDetailRouteRequest() {
        super(NftWeb3Cmds.COLLECTION_DETAIL_IC);
    }
}
