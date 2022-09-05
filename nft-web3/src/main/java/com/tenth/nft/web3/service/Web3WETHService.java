package com.tenth.nft.web3.service;

import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.wallet.utils.BigNumberUtils;
import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tenth.nft.solidity.WETHContract;
import com.tenth.nft.web3.vo.*;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author shijie
 */
@Service
public class Web3WETHService {

    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private Web3WalletService walletService;

    public WETHDepositResponse createDeposit(WETHDepositRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        BigDecimal value = new BigDecimal(request.getValue());
        value = Convert.toWei(value, Convert.Unit.ETHER);

        WETHContract wethContract = tpulseContractHelper.getWETHContract();
        String txnData = wethContract.deposit().encodeFunctionCall();
        String txnFrom = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        String txnTo = wethContract.getContractAddress();
        String txnValue = value.toBigInteger().toString();
        return new WETHDepositResponse(txnFrom, txnValue, txnTo, txnData);

    }

    public WETHWithDrawResponse createWithDraw(WETHWithDrawRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        BigDecimal value = new BigDecimal(request.getValue());
        value = Convert.toWei(value, Convert.Unit.ETHER);

        WETHContract wethContract = tpulseContractHelper.getWETHContract();
        String txnData = wethContract.withdraw(value.toBigInteger()).encodeFunctionCall();
        String txnFrom = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        String txnTo = wethContract.getContractAddress();
        String txnValue = BigInteger.ZERO.toString();
        return new WETHWithDrawResponse(txnFrom, txnValue, txnTo, txnData);

    }

    public WETHApprovalCreateResponse checkApproval() {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String uidAddress = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        WETHContract wethContract = tpulseContractHelper.getWETHContract();
        String txnValue = wethContract
                .approve(tpulseContractHelper.getContractAddress(), BigNumberUtils.BIG_NUMBER_MAX)
                .encodeFunctionCall();
        String txnTo = tpulseContractHelper.getWETHContract().getContractAddress();
        String from = uidAddress;

        return new WETHApprovalCreateResponse(from, txnTo, txnValue);
    }

    public void confirmApproval(WETHApprovalConfirmRequest request) {

        ContractTransactionReceipt receipt = tpulseContractHelper.getTxn(request.getTxn());
        if(receipt.isSuccess()){
            walletService.updateWethApprovalState(true);
        }
    }

    public TxnStatus checkTxn(WETHTxnCheckRequest request) {
        ContractTransactionReceipt receipt = tpulseContractHelper.getTxn(request.getTxn());
        if(receipt.isSuccess()){
            return TxnStatus.SUCCESS;
        }else if(receipt.isFail()){
            return TxnStatus.FAIL;
        }
        return TxnStatus.PENDING;
    }
}
