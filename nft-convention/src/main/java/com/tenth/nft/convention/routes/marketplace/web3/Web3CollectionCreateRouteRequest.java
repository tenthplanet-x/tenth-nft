package com.tenth.nft.convention.routes.marketplace.web3;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.cmd.NftWeb3Cmds;
import com.tenth.nft.convention.routes.marketplace.AbsCollectionCreateRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3CollectionCreateRouteRequest extends AbsCollectionCreateRouteRequest {

    public Web3CollectionCreateRouteRequest() {
        super(NftWeb3Cmds.COLLECTION_CREATE_IC);
    }
}
