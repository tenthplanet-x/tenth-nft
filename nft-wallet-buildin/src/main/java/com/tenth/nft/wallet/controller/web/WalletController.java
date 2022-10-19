package com.tenth.nft.wallet.controller.web;

import com.tenth.nft.wallet.WalletPaths;
import com.tenth.nft.wallet.dto.WalletProfileDTO;
import com.tenth.nft.wallet.service.WalletService;
import com.tenth.nft.wallet.service.WalletSettingService;
import com.tenth.nft.wallet.vo.WalletSettingRequest;
import com.tpulse.commons.validation.Validations;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shijie
 */
@RestController
@HttpRoute(userAuth = true)
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletSettingService walletSettingService;

    @RequestMapping(WalletPaths.WALLET_PROFILE)
    public Response getBalance(){
        WalletProfileDTO profile = walletService.getProfile();
        return Response.successBuilder().data(profile).build();
    }

    @RequestMapping(WalletPaths.WALLET_SETTING)
    public Response setting(@RequestBody WalletSettingRequest request){
        Validations.check(request);
        walletSettingService.setting(request);
        return Response.successBuilder().build();
    }

}
