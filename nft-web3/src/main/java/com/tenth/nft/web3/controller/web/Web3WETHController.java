package com.tenth.nft.web3.controller.web;

import com.tenth.nft.web3.Web3WalletPaths;
import com.tenth.nft.web3.service.Web3WETHService;
import com.tenth.nft.web3.vo.WETHDepositRequest;
import com.tenth.nft.web3.vo.WETHDepositResponse;
import com.tenth.nft.web3.vo.WETHWithDrawRequest;
import com.tenth.nft.web3.vo.WETHWithDrawResponse;
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
public class Web3WETHController {

    @Autowired
    public Web3WETHService web3WETHService;

    @RequestMapping(Web3WalletPaths.WETH_DEPOSIT)
    public Response createDeposit(@RequestBody WETHDepositRequest request){
        Validations.check(request);
        WETHDepositResponse response = web3WETHService.createDeposit(request);
        return Response.successBuilder().data(response).build();
    }

    @RequestMapping(Web3WalletPaths.WETH_WITHDRAW)
    public Response createWithDraw(@RequestBody WETHWithDrawRequest request){
        Validations.check(request);
        WETHWithDrawResponse response = web3WETHService.createWithDraw(request);
        return Response.successBuilder().data(response).build();
    }

}
