package com.tenth.nft.convention.routes.wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class WalletRechargeRouteRequest extends AbsRouteRequest<NftWallet.RECHARGE_IC, NftWallet.RECHARGE_IS> {

    public WalletRechargeRouteRequest() {
        super(NftInnerCmds.RECHARGE_IC, false, false);
    }
}
