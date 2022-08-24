package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class PaymentReceiveRouteRequest extends AbsRouteRequest<NftExchange.PAYMENT_RECEIVE_IC, NftExchange.PAYMENT_RECEIVE_IS> {

    public PaymentReceiveRouteRequest() {
        super(NftInnerCmds.PAYMENT_RECEIVE_IC, false, false);
    }
}
