package com.tenth.nft.convention.routes.search;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class AssetsRouteRequest extends AbsRouteRequest<NftSearch.ASSETS_IC, NftSearch.ASSETS_IS> {

    public AssetsRouteRequest() {
        super(NftInnerCmds.ASSETS_IC, false, false);
    }
}
