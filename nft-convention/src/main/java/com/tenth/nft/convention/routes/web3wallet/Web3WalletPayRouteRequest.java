package com.tenth.nft.convention.routes.web3wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWallet;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3WalletPayRouteRequest extends AbsRouteRequest<NftWeb3Wallet.WEB3_BILL_PAY_IC, NftWeb3Wallet.WEB3_BILL_PAY_IS> {

    public Web3WalletPayRouteRequest() {
        super(NftInnerCmds.WEB3_BILL_PAY_IC, false, false);
    }
}
