package com.tenth.nft.convention.routes.operation;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftOperation;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class NftCurrencyRouteRequest extends AbsRouteRequest<NftOperation.NFT_CURRENCY_IC, NftOperation.NFT_CURRENCY_IS> {

    public NftCurrencyRouteRequest() {
        super(NftInnerCmds.NFT_CURRENCY_IC, false, false);
    }
}
