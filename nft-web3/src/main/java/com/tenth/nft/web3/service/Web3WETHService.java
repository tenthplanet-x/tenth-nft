package com.tenth.nft.web3.service;

import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.convention.wallet.utils.BigNumberUtils;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.convention.web3.sign.StructContentHash;
import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.convention.web3.utils.WalletBridgeUrl;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tenth.nft.solidity.WETHContract;
import com.tenth.nft.web3.dao.Web3WalletBillDao;
import com.tenth.nft.web3.entity.Web3WalletBill;
import com.tenth.nft.web3.entity.Web3WalletBillProfit;
import com.tenth.nft.web3.vo.*;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.json.JsonUtil;
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
    @Autowired
    private Web3WalletBillDao web3WalletBillDao;

    public WETHDepositResponse createDeposit(WETHDepositRequest request) throws Exception {

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

        Web3WalletBill bill = new Web3WalletBill();
        bill.setUid(uid);
        bill.setActivityCfgId(WalletOrderType.SwapExpense.getActivityCfgId());
        bill.setCurrency(web3Properties.getMainCurrency());
        bill.setBlockchain(web3Properties.getBlockchain());
        bill.setValue(request.getValue());
        bill.setAccountId(txnFrom);
        bill.setProductCode(WalletProductCode.WEB3_WRAP.name());
        bill.setProductId(web3Properties.getWethAddress());
        bill.setProductCode(WalletProductCode.WEB3_WRAP.name());
        bill.setMerchantType(WalletMerchantType.WETH.name());
        bill.setMerchantId(wethContract.getContractAddress());
        bill.setCreatedAt(System.currentTimeMillis());
        bill.setExpiredAt(WalletTimes.getExpiredAt());
        String content = StructContentHash.wrap(
                JsonUtil.toJson(bill),
                web3Properties.getRsa().getPrivateKey()
        );
        WETHDepositResponse response = new WETHDepositResponse(txnFrom, txnValue, txnTo, txnData, content);

        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .transaction()
                .put("from", response.getFrom())
                .put("txnTo", response.getTxnTo())
                .put("txnValue", response.getTxnValue())
                .put("txnData", response.getTxnData())
                .put("content", response.getContent())
                .build();
        response.setWalletBridgeUrl(walletBridgeUrl);

        return response;

    }

    public WETHWithDrawResponse createWithDraw(WETHWithDrawRequest request) throws Exception{

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

        Web3WalletBill bill = new Web3WalletBill();
        bill.setUid(uid);
        bill.setAccountId(txnFrom);
        bill.setActivityCfgId(WalletOrderType.SwapIncome.getActivityCfgId());
        bill.setBlockchain(web3Properties.getBlockchain());
        bill.setCurrency(web3Properties.getMainCurrency());
        bill.setValue(txnValue);
        bill.setProductCode(WalletProductCode.WEB3_WRAP.name());
        bill.setProductId(web3Properties.getWethAddress());
        bill.setProductCode(WalletProductCode.WEB3_WRAP.name());
        bill.setMerchantType(WalletMerchantType.WETH.name());
        bill.setMerchantId(wethContract.getContractAddress());
        bill.setCreatedAt(System.currentTimeMillis());
        bill.setExpiredAt(WalletTimes.getExpiredAt());
        String content = StructContentHash.wrap(
                JsonUtil.toJson(bill),
                web3Properties.getRsa().getPrivateKey()
        );

        WETHWithDrawResponse response = new WETHWithDrawResponse(txnFrom, txnValue, txnTo, txnData, content);
        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .transaction()
                .put("from", response.getFrom())
                .put("txnTo", response.getTxnTo())
                .put("txnValue", response.getTxnValue())
                .put("txnData", response.getTxnData())
                .put("content", response.getContent())
                .build();
        response.setWalletBridgeUrl(walletBridgeUrl);
        return response;

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

        WETHApprovalCreateResponse response = new WETHApprovalCreateResponse(from, txnTo, txnValue);
        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .transaction()
                .put("from", response.getFrom())
                .put("txnTo", response.getTxnTo())
                .put("txnData", response.getTxnData())
                .build();
        response.setWalletBridgeUrl(walletBridgeUrl);

        return response;
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

            String transactionId = receipt.getReceipt().getTransactionHash();
            Web3WalletBill bill = JsonUtil.fromJson(StructContentHash.unwrap(request.getContent()), Web3WalletBill.class);
            bill.setNotified(true);
            bill.setState(WalletBillState.COMPLETE.name());
            bill.setTransactionId(transactionId);
            bill.setOutOrderId(transactionId);
            bill.setUsedGasValue(receipt.getUsedGasValue());
            web3WalletBillDao.insert(bill);

            return TxnStatus.SUCCESS;
        }else if(receipt.isFail()){
            return TxnStatus.FAIL;
        }
        return TxnStatus.PENDING;
    }
}
