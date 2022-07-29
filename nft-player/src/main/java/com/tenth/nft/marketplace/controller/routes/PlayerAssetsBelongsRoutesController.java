package com.tenth.nft.marketplace.controller.routes;

import com.tenth.nft.convention.routes.player.AssetsBelongsUpdateRouteRequest;
import com.tenth.nft.marketplace.service.PlayerAssetsBelongsService;
import com.tenth.nft.protobuf.NftPlayer;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class PlayerAssetsBelongsRoutesController {

    @Autowired
    private PlayerAssetsBelongsService playerAssetsBelongsService;

    @RouteRequestMapping(AssetsBelongsUpdateRouteRequest.class)
    public void updateAssetsBelongs(NftPlayer.ASSETS_BELONGS_UPDATE_IC request){
        playerAssetsBelongsService.updateAssetsBelongs(request);
    }

}
