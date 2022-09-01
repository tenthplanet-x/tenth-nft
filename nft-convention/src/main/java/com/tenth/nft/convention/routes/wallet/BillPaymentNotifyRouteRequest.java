package com.tenth.nft.convention.routes.wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BillPaymentNotifyRouteRequest extends AbsRouteRequest<NftWallet.BILL_PAYMENT_NOTIFY_IC, Router.AsynNullResponse> {

    public BillPaymentNotifyRouteRequest() {
        super(NftInnerCmds.BILL_PAYMENT_NOTIFY_IC, true, false);
    }
}
