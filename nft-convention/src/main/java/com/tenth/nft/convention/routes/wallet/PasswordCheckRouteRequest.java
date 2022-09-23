package com.tenth.nft.convention.routes.wallet;

import com.tenth.nft.convention.cmd.NftInnerCmds;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.router.requestmapping.AbsRouteRequest;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class PasswordCheckRouteRequest extends AbsRouteRequest<NftWallet.PASSWORD_CHECK_IC, NftWallet.PASSWORD_CHECK_IS> {

    public PasswordCheckRouteRequest() {
        super(NftInnerCmds.WALLET_PASSWORD_CHECK_IC, false, false);
    }
}
