package com.tenth.nft.wallet.controller.routes;

import com.tenth.nft.convention.routes.wallet.PasswordCheckRouteRequest;
import com.tenth.nft.protobuf.NftWallet;
import com.tenth.nft.wallet.service.WalletService;
import com.tenth.nft.wallet.service.WalletSettingService;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class BuildInWalletRoutesController {

    @Autowired
    private WalletSettingService walletSettingService;

    @RouteRequestMapping(PasswordCheckRouteRequest.class)
    public NftWallet.PASSWORD_CHECK_IS checkPassword(NftWallet.PASSWORD_CHECK_IC request){
        return walletSettingService.checkPassword(request);
    }
}
