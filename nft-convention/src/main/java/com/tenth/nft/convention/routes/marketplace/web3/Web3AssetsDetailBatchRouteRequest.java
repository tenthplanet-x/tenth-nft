package com.tenth.nft.convention.routes.marketplace.web3;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.routes.marketplace.AbsAssetsDetailBatchRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3AssetsDetailBatchRouteRequest extends AbsAssetsDetailBatchRouteRequest {

    public Web3AssetsDetailBatchRouteRequest() {
        super(NftInnerCmds.WEB3_ASSETS_DETAIL_BATCH_IC);
    }
}
