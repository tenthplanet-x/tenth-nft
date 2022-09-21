package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class AssetsCreateRouteRequest extends AbsRouteRequest<NftMarketplace.ASSETS_CREATE_IC, NftMarketplace.ASSETS_CREATE_IS> {

    public AssetsCreateRouteRequest() {
        super(NftInnerCmds.ASSETS_CREATE_IC, false, false);
    }
}
