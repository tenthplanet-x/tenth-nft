package com.tenth.nft.web3.controller.routes;

import com.tenth.nft.convention.routes.web3wallet.Web3TxnCheckRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletPayRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletProfileRouteRequest;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.web3.service.Web3WalletBillService;
import com.tenth.nft.web3.service.Web3WalletService;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class Web3WalletRouteController {

    @Autowired
    private Web3WalletBillService web3WalletBillService;
    @Autowired
    private Web3WalletService web3WalletService;

    @RouteRequestMapping(Web3TxnCheckRouteRequest.class)
    public void checkTxnState(NftWeb3Wallet.WEB3_TXN_CHECK_IC request){
        web3WalletBillService.txnStateCheck(request);
    }

    @RouteRequestMapping(Web3WalletBalanceRouteRequest.class)
    public NftWeb3Wallet.WEB3_WALLET_BALANCE_IS walletBalance(NftWeb3Wallet.WEB3_WALLET_BALANCE_IC request){
        return web3WalletBillService.walletBalance(request);
    }

    @RouteRequestMapping(Web3WalletProfileRouteRequest.class)
    public NftWeb3Wallet.WEB3_WALLET_RPOFILE_IS walletProfile(NftWeb3Wallet.WEB3_WALLET_RPOFILE_IC request){
        return web3WalletService.walletProfile(request);
    }
}
