package com.tenth.nft.convention.routes.exchange;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3PaymentConfirmRouteRequest extends AbsRouteRequest<NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IC, NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS> {

    public Web3PaymentConfirmRouteRequest() {
        super(NftInnerCmds.WEB3_PAYMENT_CONFIRM_IC, false, false);
    }
}
