package com.tenth.nft.convention.routes.search;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class CurrencyRatesRouteRequest extends AbsRouteRequest<NftSearch.NFT_CURRENCY_RATES_IC, NftSearch.NFT_CURRENCY_RATES_IS> {

    public CurrencyRatesRouteRequest() {
        super(NftInnerCmds.NFT_CURRENCY_RATES_IC, false, false);
    }
}
