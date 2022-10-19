package com.tenth.nft.convention.routes.marketplace.buildin;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BuildInAcceptReceiptRouteRequest extends AbsRouteRequest<NftExchange.PAYMENT_RECEIVE_IC, NftExchange.PAYMENT_RECEIVE_IS> {

    public BuildInAcceptReceiptRouteRequest() {
        super(NftInnerCmds.BUILDIN_ACCEPT_WALLET_RECEIPT_IC, false, false);
    }
}
