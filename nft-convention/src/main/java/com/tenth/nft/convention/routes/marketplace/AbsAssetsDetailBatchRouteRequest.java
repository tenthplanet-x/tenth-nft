package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
public abstract class AbsAssetsDetailBatchRouteRequest extends AbsRouteRequest<NftMarketplace.ASSETS_DETAIL_BATCH_IC, NftMarketplace.ASSETS_DETAIL_BATCH_IS> {

    public AbsAssetsDetailBatchRouteRequest(int cmd) {
        super(cmd, false, false);
    }
}
