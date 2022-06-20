package com.tenth.nft.convention.routes;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.Search;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class ItemsRebuildRouteRequest extends AbsRouteRequest<Search.NFT_ITEM_REBUILD_IC, Router.AsynNullResponse> {

    public ItemsRebuildRouteRequest() {
        super(NftInnerCmds.NFT_ITEM_REBUILD_IC, true, false);
    }

}
