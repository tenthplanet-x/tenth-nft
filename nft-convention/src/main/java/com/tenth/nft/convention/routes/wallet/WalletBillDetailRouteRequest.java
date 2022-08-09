package com.tenth.nft.convention.routes.wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class WalletBillDetailRouteRequest extends AbsRouteRequest<NftWallet.BILL_DETAIL_IC, NftWallet.BILL_DETAIL_IS> {

    public WalletBillDetailRouteRequest() {
        super(NftInnerCmds.BILL_DETAIL_IC, false, false);
    }
}
