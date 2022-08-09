package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class AssetsDetailRouteRequest extends AbsRouteRequest<NftMarketplace.ASSETS_DETAIL_IC, NftMarketplace.ASSETS_DETAIL_IS> {

    public AssetsDetailRouteRequest() {
        super(NftInnerCmds.ASSETS_DETAIL_IC, false, false);
    }
}
