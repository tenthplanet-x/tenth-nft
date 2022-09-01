package com.tenth.nft.web3.service;

import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.exchange.Web3PaymentConfirmRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3TxnCheckRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletPayRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.wallet.WalletBillState;
import com.tenth.nft.convention.wallet.WalletMerchantType;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.WalletToken;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tenth.nft.web3.dao.Web3WalletBillDao;
import com.tenth.nft.web3.dao.Web3WalletDao;
import com.tenth.nft.web3.dao.Web3WalletEventDao;
import com.tenth.nft.web3.dao.expression.Web3WalletBillQuery;
import com.tenth.nft.web3.dao.expression.Web3WalletBillUpdate;
import com.tenth.nft.web3.dao.expression.Web3WalletQuery;
import com.tenth.nft.web3.dto.Web3WalletBillDTO;
import com.tenth.nft.web3.entity.Web3Wallet;
import com.tenth.nft.web3.entity.Web3WalletBill;
import com.tenth.nft.web3.entity.Web3WalletBillProfit;
import com.tenth.nft.web3.vo.Web3WalletBillPayRequest;
import com.tenth.nft.web3.vo.Web3WalletBillStateRequest;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class Web3WalletBillService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Web3WalletBillService.class);

    @Autowired
    private Web3WalletDao web3WalletDao;
    @Autowired
    private Web3WalletBillDao web3WalletBillDao;
    @Autowired
    private Web3WalletEventDao web3WalletEventDao;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;


    public Web3WalletBillDTO pay(Web3WalletBillPayRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftWeb3Wallet.WEB3_BILL_PAY_IC innerRequest = NftWeb3Wallet.WEB3_BILL_PAY_IC.newBuilder()
                .setUid(uid)
                .setToken(request.getToken())
                .setTxn(request.getTxn())
                .build();

        NftWeb3Wallet.Web3BillDTO web3BillDTO = routeClient.send(
                innerRequest,
                Web3WalletPayRouteRequest.class
        ).getBill();

        Web3WalletBillDTO billDTO = Web3WalletBillDTO.from(web3BillDTO);
        return billDTO;

    }

    public NftWeb3Wallet.WEB3_BILL_PAY_IS pay(NftWeb3Wallet.WEB3_BILL_PAY_IC request){

        //token verify
        WalletToken walletToken = WalletToken.decode(request.getToken());
        if(!walletToken.verify(web3Properties.getRsa().getPublickey())){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN);
        }

        //check
        WalletOrderBizContent bizContent = walletToken.getBizContent();
//        BigDecimal balance = tpulseContractHelper.getBalance(bizContent.getCurrency(), accountId);
//        if(balance.compareTo(new BigDecimal(bizContent.getValue())) > 0){
//            throw BizException.newInstance(NftExchangeErrorCodes.WEB3WALLET_PAY_EXCEPTION_LACK_OF_BALANCE);
//        }

        //exist check
        Web3WalletBill walletBill = web3WalletBillDao.findOne(Web3WalletBillQuery.newBuilder()
                .uid(request.getUid())
                .productCode(bizContent.getProductCode())
                .outOrderId(bizContent.getOutOrderId())
                .build()
        );

        String address = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(request.getUid())
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();


        if(null == walletBill){
            walletBill = new Web3WalletBill();
            walletBill.setActivityCfgId(bizContent.getActivityCfgId());
            walletBill.setUid(request.getUid());
            walletBill.setAccountId(address);
            walletBill.setProductCode(bizContent.getProductCode());
            walletBill.setProductId(bizContent.getProductId());
            walletBill.setOutOrderId(bizContent.getOutOrderId());
            walletBill.setMerchantType(bizContent.getMerchantType());
            walletBill.setMerchantId(bizContent.getMerchantId());
            walletBill.setCurrency(bizContent.getCurrency());
            walletBill.setValue(bizContent.getValue());
            walletBill.setExpiredAt(bizContent.getExpiredAt());
            walletBill.setCreatedAt(System.currentTimeMillis());
            walletBill.setUpdatedAt(walletBill.getCreatedAt());
            walletBill.setState(WalletBillState.CREATE.name());
            walletBill.setRemark(bizContent.getRemark());
            walletBill.setTransactionId(request.getTxn());
            walletBill.setProfits(convert(bizContent.getProfits()));
            web3WalletBillDao.insert(walletBill);
        }

        WalletBillState currentState = WalletBillState.valueOf(walletBill.getState());
        if(!WalletBillState.CREATE.equals(currentState)){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN);
        }

        //Use async task to check the txn state
        routeClient.send(
                NftWeb3Wallet.WEB3_TXN_CHECK_IC.newBuilder()
                        .setUid(walletBill.getUid())
                        .setBillId(walletBill.getId())
                        .build(),
                Web3TxnCheckRouteRequest.class
        );

        //changeState(walletBill, WalletBillState.PAYED, "ok");

        NftWeb3Wallet.Web3BillDTO web3BillDTO = toWeb3BillDTO(walletBill);
        return NftWeb3Wallet.WEB3_BILL_PAY_IS.newBuilder()
                .setBill(web3BillDTO)
                .build();

    }

    private List<Web3WalletBillProfit> convert(List<WalletOrderBizContent.Profit> profits) {

        return profits.stream().map(profit -> {
            Web3WalletBillProfit web3WalletBillProfit = new Web3WalletBillProfit();
            web3WalletBillProfit.setTo(profit.getTo());
            web3WalletBillProfit.setToAddress(profit.getToAddress());
            web3WalletBillProfit.setCurrency(profit.getCurrency());
            web3WalletBillProfit.setValue(profit.getValue());
            web3WalletBillProfit.setActivityCfgId(profit.getActivityCfgId());
            return web3WalletBillProfit;
        }).collect(Collectors.toList());
    }

    private NftWeb3Wallet.Web3BillDTO toWeb3BillDTO(Web3WalletBill web3WalletBill) {

        NftWeb3Wallet.Web3BillDTO.Builder builder = NftWeb3Wallet.Web3BillDTO.newBuilder();
        builder.setUid(web3WalletBill.getUid());
        builder.setAccountId(web3WalletBill.getAccountId());
        builder.setBillId(web3WalletBill.getId());
        builder.setProductCode(web3WalletBill.getProductCode());
        builder.setProductId(web3WalletBill.getProductId());
        builder.setOutOrderId(web3WalletBill.getOutOrderId());
        builder.setMerchantType(web3WalletBill.getMerchantType());
        builder.setMerchantId(web3WalletBill.getMerchantId());
        builder.setCurrency(web3WalletBill.getCurrency());
        builder.setValue(web3WalletBill.getValue());
        builder.setCreatedAt(web3WalletBill.getCreatedAt());
        builder.setActivityCfgId(web3WalletBill.getActivityCfgId());
        builder.setState(web3WalletBill.getState());

        return builder.build();
    }

    private void changeState(Web3WalletBill walletBill, WalletBillState state, String remark) {
        web3WalletBillDao.update(
                Web3WalletBillQuery.newBuilder()
                        .accountId(walletBill.getAccountId())
                        .blockchain(walletBill.getBlockchain())
                        .id(walletBill.getId())
                        .build(),
                Web3WalletBillUpdate.newBuilder()
                        .setState(state.name())
                        .setRemark(remark)
                        .build()
        );
    }


    public Web3WalletBillDTO state(Web3WalletBillStateRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        //get web3 accountId
        Web3Wallet web3Wallet = web3WalletDao.findOne(Web3WalletQuery.newBuilder().uid(uid).build());

        Web3WalletBillDTO billDTO = Web3WalletBillDTO.from(
                web3WalletBillDao.findOne(
                        Web3WalletBillQuery.newBuilder()
                                .accountId(web3Wallet.getWalletAccountId())
                                .blockchain(web3Wallet.getBlockchain())
                                .id(request.getId())
                                .build()
                )
        );

        return billDTO;
    }

    /**
     * check the txn state in blockchain
     * @param request
     */
    public void txnStateCheck(NftWeb3Wallet.WEB3_TXN_CHECK_IC request){

        //get web3 accountId
        Web3Wallet web3Wallet = web3WalletDao.findOne(Web3WalletQuery.newBuilder().uid(request.getUid()).build());

        SimpleQuery web3WalletBillQuery = Web3WalletBillQuery.newBuilder()
                .accountId(web3Wallet.getWalletAccountId())
                .id(request.getBillId())
                .build();
        Web3WalletBill web3WalletBill = web3WalletBillDao.findOne(web3WalletBillQuery);
        String txn = web3WalletBill.getTransactionId();
        if(WalletBillState.CREATE.name().equals(web3WalletBill.getState())){
            ContractTransactionReceipt receipt = tpulseContractHelper.getTxn(txn);
            if(receipt.isSuccess()){

                //change to complete
                changeState(web3WalletBill, WalletBillState.PAYED, "");

                try{
                    routeClient.send(
                            NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IC.newBuilder()
                                    .setAssetsId(Long.valueOf(web3WalletBill.getProductId()))
                                    .setOrderId(web3WalletBill.getOutOrderId())
                                    .setState(WalletBillState.PAYED.name())
                                    .setTxn(txn)
                                    .build()
                            , Web3PaymentConfirmRouteRequest.class);

                    web3WalletBillDao.update(
                            web3WalletBillQuery,
                            Web3WalletBillUpdate.newBuilder()
                                    .setNotified(true)
                                    .setUsedGasValue(receipt.getUsedGasValue())
                                    .build()
                    );

                    //dispatch profits
                    for(Web3WalletBillProfit profit: web3WalletBill.getProfits()){
                        Web3WalletBill payForBill = copyWithBizContent(web3WalletBill);
                        payForBill.setUid(profit.getTo());
                        payForBill.setAccountId(profit.getToAddress());
                        payForBill.setActivityCfgId(profit.getActivityCfgId());
                        payForBill.setMerchantType(WalletMerchantType.PERSONAL.name());
                        payForBill.setMerchantId(String.valueOf(web3WalletBill.getUid()));
                        payForBill.setCurrency(profit.getCurrency());
                        payForBill.setValue(profit.getValue());
                        payForBill.setState(WalletBillState.COMPLETE.name());
                        web3WalletBillDao.insert(payForBill);
                    }
                    //change to complete
                    changeState(web3WalletBill, WalletBillState.COMPLETE, "");

                }catch (Exception e){
                    web3WalletBillDao.update(
                            web3WalletBillQuery,
                            Web3WalletBillUpdate.newBuilder()
                                    .setRetryInc()
                                    .build()
                    );
                    LOGGER.error("", e);
                    throw e;
                }

            }else if(receipt.isFail()){
                changeState(web3WalletBill, WalletBillState.FAIL, "");
            }

        }
    }

    private Web3WalletBill copyWithBizContent(Web3WalletBill walletBill) {
        Web3WalletBill copy = new Web3WalletBill();
        copy.setBlockchain(walletBill.getBlockchain());
        copy.setActivityCfgId(walletBill.getActivityCfgId());
        copy.setProductCode(walletBill.getProductCode());
        copy.setProductId(walletBill.getProductId());
        copy.setOutOrderId(walletBill.getOutOrderId());
        copy.setExpiredAt(walletBill.getExpiredAt());
        copy.setCurrency(walletBill.getCurrency());
        copy.setValue(walletBill.getValue());
        copy.setCreatedAt(System.currentTimeMillis());
        copy.setUpdatedAt(walletBill.getCreatedAt());
        copy.setState(WalletBillState.CREATE.name());
        copy.setRemark(walletBill.getRemark());
        copy.setTransactionId(walletBill.getTransactionId());
        return copy;
    }


    public NftWeb3Wallet.WEB3_WALLET_BALANCE_IS walletBalance(NftWeb3Wallet.WEB3_WALLET_BALANCE_IC request) {

        Web3Wallet web3Wallet = web3WalletDao.findOne(Web3WalletQuery.newBuilder().uid(request.getUid()).build());
        if(null == web3Wallet){
            throw BizException.newInstance(NftExchangeErrorCodes.WEB3WALLET_DONT_HAVE_BIND);
        }

        NftWeb3Wallet.Web3WalletBalanceDTO.Builder builder = NftWeb3Wallet.Web3WalletBalanceDTO.newBuilder();
        String address = web3Wallet.getWalletAccountId();
        builder.setAddress(address);
        builder.setCurrency(web3Properties.getMainCurrency());
        if(request.hasNeedBalance()){
            String balance = tpulseContractHelper.getBalance(web3Properties.getMainCurrency(), address).toString();
            builder.setValue(balance);
        }

        return NftWeb3Wallet.WEB3_WALLET_BALANCE_IS.newBuilder()
                .setBalance(builder.build())
                .build();

    }
}
