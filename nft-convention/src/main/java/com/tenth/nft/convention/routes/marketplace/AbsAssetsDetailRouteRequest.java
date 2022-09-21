package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public abstract class AbsAssetsDetailRouteRequest extends AbsRouteRequest<NftMarketplace.ASSETS_DETAIL_IC, NftMarketplace.ASSETS_DETAIL_IS> {

    public AbsAssetsDetailRouteRequest(int cmd) {
        super(cmd, false, false);
    }
}
