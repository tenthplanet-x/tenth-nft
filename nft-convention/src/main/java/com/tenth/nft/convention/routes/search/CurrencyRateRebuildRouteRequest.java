package com.tenth.nft.convention.routes.search;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class CurrencyRateRebuildRouteRequest extends AbsRouteRequest<NftSearch.NFT_CURRENCY_RATE_REBUILD_IC, NftSearch.NFT_CURRENCY_RATE_REBUILD_IS> {

    public CurrencyRateRebuildRouteRequest() {
        super(NftInnerCmds.NFT_CURRENCY_RATE_REBUILD_IC, true, false);
    }
}
