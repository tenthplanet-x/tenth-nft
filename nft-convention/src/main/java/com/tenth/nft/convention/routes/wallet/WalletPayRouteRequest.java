package com.tenth.nft.convention.routes.wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class WalletPayRouteRequest extends AbsRouteRequest<NftWallet.BILL_PAY_IC, NftWallet.BILL_PAY_IS> {

    public WalletPayRouteRequest() {
        super(NftInnerCmds.BILL_PAY_IC, false, false);
    }
}
