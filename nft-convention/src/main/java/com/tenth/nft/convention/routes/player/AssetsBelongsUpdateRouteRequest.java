package com.tenth.nft.convention.routes.player;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftPlayer;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class AssetsBelongsUpdateRouteRequest extends AbsRouteRequest<NftPlayer.ASSETS_BELONGS_UPDATE_IC, Router.AsynNullResponse> {

    public AssetsBelongsUpdateRouteRequest() {
        super(NftInnerCmds.ASSETS_BELONGS_UPDATE_IC, true, false);
    }
}
