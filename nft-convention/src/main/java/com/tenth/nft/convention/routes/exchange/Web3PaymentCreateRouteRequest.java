package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3PaymentCreateRouteRequest extends AbsRouteRequest<NftWeb3Exchange.WEB3_PAYMENT_CREATE_IC, NftWeb3Exchange.WEB3_PAYMENT_CREATE_IS> {

    public Web3PaymentCreateRouteRequest() {
        super(NftInnerCmds.WEB3_PAYMENT_CREATE_IC, false, false);
    }
}
