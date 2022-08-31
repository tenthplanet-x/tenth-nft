package com.tenth.nft.convention.routes.marketplace;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class AssetsMintRouteRequest extends AbsRouteRequest<NftMarketplace.ASSETS_MINT_IC, Router.AsynNullResponse> {

    public AssetsMintRouteRequest() {
        super(NftInnerCmds.ASSETS_MINT_IC, true, false);
    }

}
