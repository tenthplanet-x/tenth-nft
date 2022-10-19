package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
public abstract class AbsAssetsCreateRouteRequest extends AbsRouteRequest<NftMarketplace.ASSETS_CREATE_IC, NftMarketplace.ASSETS_CREATE_IS> {

    public AbsAssetsCreateRouteRequest(int cmd) {
        super(cmd, false, false);
    }
}
