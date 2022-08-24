package com.tenth.nft.web3.service;

import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.routes.exchange.PaymentReceiveRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletPayRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.wallet.WalletBillState;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.Web3WalletToken;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.web3.dao.Web3WalletBillDao;
import com.tenth.nft.web3.dao.Web3WalletDao;
import com.tenth.nft.web3.dao.Web3WalletEventDao;
import com.tenth.nft.web3.dao.expression.Web3WalletBillQuery;
import com.tenth.nft.web3.dao.expression.Web3WalletBillUpdate;
import com.tenth.nft.web3.dao.expression.Web3WalletQuery;
import com.tenth.nft.web3.dto.Web3WalletBillDTO;
import com.tenth.nft.web3.entity.Web3Wallet;
import com.tenth.nft.web3.entity.Web3WalletBill;
import com.tenth.nft.web3.vo.Web3WalletBillPayRequest;
import com.tenth.nft.web3.vo.Web3WalletBillStateRequest;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class Web3WalletBillService {

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
    @Value("${web3.rsa.public-key}")
    private String publicKey;

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
        Web3WalletToken walletToken = Web3WalletToken.decode(request.getToken());
        if(!walletToken.verify(publicKey)){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN);
        }

        //get web3 accountId
        Web3Wallet web3Wallet = web3WalletDao.findOne(Web3WalletQuery.newBuilder().uid(request.getUid()).build());
        String accountId = web3Wallet.getWalletAccountId();

        WalletOrderBizContent bizContent = walletToken.getBizContent();
        //exist check
        Web3WalletBill walletBill = web3WalletBillDao.findOne(Web3WalletBillQuery.newBuilder()
                .accountId(accountId)
                .productCode(bizContent.getProductCode())
                .outOrderId(bizContent.getOutOrderId())
                .build()
        );
        if(null == walletBill){
            walletBill = new Web3WalletBill();
            walletBill.setActivityCfgId(bizContent.getActivityCfgId());
            walletBill.setUid(request.getUid());
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
            web3WalletBillDao.insert(walletBill);
        }

        WalletBillState currentState = WalletBillState.valueOf(walletBill.getState());
        if(WalletBillState.PAYED.equals(currentState) || WalletBillState.FAIL.equals(currentState)){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN);
        }

        try{
            //password check
            //walletSettingService.checkPassword(request.getUid(), request.getPassword());
            //verify balance
            //walletService.checkBalance(request.getUid(), bizContent.getCurrency(), bizContent.getValue());
            //TODO biz check
            changeState(walletBill, WalletBillState.PAYED, "ok");
        }catch (BizException e){
            changeState(walletBill, WalletBillState.FAIL, "verify error");
            throw e;
        }

        NftWeb3Wallet.Web3BillDTO web3BillDTO = toWeb3BillDTO(walletBill);
        return NftWeb3Wallet.WEB3_BILL_PAY_IS.newBuilder()
                .setBill(web3BillDTO)
                .build();

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
}
