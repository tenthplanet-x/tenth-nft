package com.tenth.nft.convention.routes.marketplace.buildin;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInBuyReceiptRouteRequest extends AbsRouteRequest<NftExchange.PAYMENT_RECEIVE_IC, NftExchange.PAYMENT_RECEIVE_IS> {

    public BuildInBuyReceiptRouteRequest() {
        super(NftInnerCmds.BUILDIN_BUY_RECEIPT_IC, false, false);
    }
}
