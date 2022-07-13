package com.tenth.nft.convention.routes;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class ExternalCategoryRebuildRouteRequest extends AbsRouteRequest<NftSearch.EXTERNAL_NFT_CATEGORY_REBUILD_IC, Router.AsynNullResponse> {

    public ExternalCategoryRebuildRouteRequest() {
        super(NftInnerCmds.EXTERNAL_NFT_CATEGORY_REBUILD_IC, true, false);
    }

}
