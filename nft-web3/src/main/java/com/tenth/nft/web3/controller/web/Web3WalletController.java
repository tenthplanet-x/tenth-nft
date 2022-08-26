package com.tenth.nft.web3.controller;

import com.tenth.nft.web3.Web3WalletPaths;
import com.tenth.nft.web3.dto.Web3WalletBalance;
import com.tenth.nft.web3.service.Web3WalletService;
import com.tenth.nft.web3.vo.Web3WalletBindRequest;
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
public class Web3WalletController {

    @Autowired
    private Web3WalletService web3WalletService;

    @RequestMapping(Web3WalletPaths.WALLET_BALANCE)
    public Response balance() throws Exception{
        Web3WalletBalance balance = web3WalletService.balance();
        return Response.successBuilder().data(balance).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_BIND)
    public Response bind(@RequestBody Web3WalletBindRequest request) throws Exception{

        Validations.check(request);
        //TODO Need check the signature
        web3WalletService.bind(request);
        return Response.successBuilder().build();

    }
}
