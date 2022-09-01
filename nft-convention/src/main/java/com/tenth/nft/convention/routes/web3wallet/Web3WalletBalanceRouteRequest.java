package com.tenth.nft.convention.routes.web3wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3WalletBalanceRouteRequest extends AbsRouteRequest<NftWeb3Wallet.WEB3_WALLET_BALANCE_IC, NftWeb3Wallet.WEB3_WALLET_BALANCE_IS> {

    public Web3WalletBalanceRouteRequest() {
        super(NftInnerCmds.WEB3_WALLET_BALANCE_IC, false, false);
    }
}
