package com.tenth.nft.convention.routes.wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class BillIncomeTriggerRouteRequest extends AbsRouteRequest<NftWallet.BILL_INCOME_TRIGGER_IC, NftWallet.BILL_INCOME_TRIGGER_IS> {

    public BillIncomeTriggerRouteRequest() {
        super(NftInnerCmds.BILL_INCOME_TRIGGER_IC, true, false);
    }
}
