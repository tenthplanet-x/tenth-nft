package com.tenth.nft.web3.controller.web;

import com.tenth.nft.solidity.TpulseContractHelper;
import com.tenth.nft.web3.Web3WalletPaths;
import com.tenth.nft.web3.dto.Web3WalletBalance;
import com.tenth.nft.web3.dto.Web3WalletBindSIgnTicket;
import com.tenth.nft.web3.dto.Web3WalletDetailDTO;
import com.tenth.nft.web3.service.Web3WalletService;
import com.tenth.nft.web3.vo.*;
import com.tpulse.commons.validation.Validations;
import com.wallan.router.endpoint.core.security.HttpRoute;
import com.wallan.router.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shijie
 */
@RestController
@HttpRoute(userAuth = true)
public class Web3WalletController {

    @Autowired
    private Web3WalletService web3WalletService;


    @RequestMapping(Web3WalletPaths.WALLET_ACCOUNT)
    public Response profile() throws Exception{
        Web3WalletDetailDTO profile = web3WalletService.profile();
        return Response.successBuilder().data(profile).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_PROFILE)
    public Response profiles(@RequestBody Web3WalletProfilesRequest request) throws Exception{
        Validations.check(request);
        List<Web3WalletProfile> profiles = web3WalletService.profiles(request);
        return Response.successBuilder().data(profiles).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_CREATE_AUTH)
    public Response createAuth(){
        String walletBridgeUrl = web3WalletService.createAuth();
        return Response.successBuilder().data(walletBridgeUrl).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_BALANCE)
    public Response balance() throws Exception{
        List<Web3WalletBalance> balance = web3WalletService.balance();
        return Response.successBuilder().data(balance).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_BIND_PREPARE)
    public Response bindPrepare(@RequestBody Web3WalletBindPrepareRequest request){
        Web3WalletBindSIgnTicket ticket = web3WalletService.prepareBind(request);
        return Response.successBuilder().data(ticket).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_BIND_CONFIRM)
    public Response bind(@RequestBody Web3WalletBindRequest request) throws Exception{

        Validations.check(request);
        //TODO Need check the signature
        web3WalletService.bind(request);
        return Response.successBuilder().build();

    }

    @RequestMapping(Web3WalletPaths.WALLET_UNBIND)
    public Response unBind(){
        //TODO Need check the signature
        web3WalletService.unBind();
        return Response.successBuilder().build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_CONTRACT_APPROVAL_CREATE)
    public Response createApproval(){
        TpulseContractHelper.ApprovalTxn response = web3WalletService.createApproval();
        return Response.successBuilder().data(response).build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_CONTRACT_APPROVAL_CONFIRM)
    public Response confirmApproval(@RequestBody Web3ContractApprovalConfirmRequest request){

        Validations.check(request);
        web3WalletService.confirmApproval(request);
        return Response.successBuilder().build();
    }

    @RequestMapping(Web3WalletPaths.WALLET_CONTRACT_APPROVAL_CANCEL)
    public Response cancelApproval(){
        web3WalletService.cancelApproval();
        return Response.successBuilder().build();
    }
}
