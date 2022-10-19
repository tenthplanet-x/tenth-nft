package com.tenth.nft.convention.routes.marketplace.buildin;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.convention.routes.marketplace.AbsAssetsDetailBatchRouteRequest;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInAssetsDetailBatchRouteRequest extends AbsAssetsDetailBatchRouteRequest {

    public BuildInAssetsDetailBatchRouteRequest() {
        super(NftInnerCmds.BUILDIN_ASSETS_DETAIL_BATCH_IC);
    }
}
