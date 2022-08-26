package com.tenth.nft.convention.routes.web3wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import com.tpulse.gs.router.requestmapping.Router;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3TxnCheckRouteRequest extends AbsRouteRequest<NftWeb3Wallet.WEB3_TXN_CHECK_IC, Router.AsynNullResponse> {

    public Web3TxnCheckRouteRequest() {
        super(NftInnerCmds.WEB3_TXN_CHECK_IC, true, false);
    }
}
