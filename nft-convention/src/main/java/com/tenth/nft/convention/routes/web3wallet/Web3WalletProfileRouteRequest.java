package com.tenth.nft.convention.routes.web3wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3WalletProfileRouteRequest extends AbsRouteRequest<NftWeb3Wallet.WEB3_WALLET_RPOFILE_IC, NftWeb3Wallet.WEB3_WALLET_RPOFILE_IS> {

    public Web3WalletProfileRouteRequest() {
        super(NftInnerCmds.WEB3_WALLET_PROFILE_IC, false, false);
    }
}
