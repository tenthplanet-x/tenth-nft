package com.tenth.nft.convention.routes;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class CategoryRebuildRouteRequest extends AbsRouteRequest<NftSearch.NFT_CATEGORY_REBUILD_IC, Router.AsynNullResponse> {

    public CategoryRebuildRouteRequest() {
        super(NftInnerCmds.NFT_CATEGORY_REBUILD_IC, true, false);
    }

}
