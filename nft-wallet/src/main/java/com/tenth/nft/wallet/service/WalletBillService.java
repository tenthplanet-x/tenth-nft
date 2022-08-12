package com.tenth.nft.wallet.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.BuyReceiptPushRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.wallet.WalletPayRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletActivityConfig;
import com.tenth.nft.convention.templates.WalletActivityTemplate;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWallet;
import com.tenth.nft.wallet.dao.WalletBillDao;
import com.tenth.nft.wallet.dao.expression.WalletBillQuery;
import com.tenth.nft.wallet.dao.expression.WalletBillUpdate;
import com.tenth.nft.wallet.dto.WalletBillDTO;
import com.tenth.nft.wallet.dto.WalletBillSimpleDTO;
import com.tenth.nft.wallet.entity.WalletBill;
import com.tenth.nft.wallet.vo.BillDetailRequest;
import com.tenth.nft.wallet.vo.BillListRequest;
import com.tenth.nft.wallet.vo.BillPayRequest;
import com.tpulse.gs.config2.client.GsConfigTemplateFactory;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class WalletBillService {

    @Value("${wallet.rsa.public-key}")
    private String publicKey;
    @Autowired
    private WalletSettingService walletSettingService;
    @Autowired
    @Lazy
    private WalletService walletService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private WalletBillDao walletBillDao;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    public WalletBillDTO pay(BillPayRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        WalletBillDTO walletBillDTO = WalletBillDTO.from(
                routeClient.send(
                        NftWallet.BILL_PAY_IC.newBuilder()
                                .setUid(uid)
                                .setToken(request.getToken())
                                .setPassword(request.getPassword())
                                .build(),
                        WalletPayRouteRequest.class
                ).getBill()
        );

        return walletBillDTO;
    }

    public NftWallet.BILL_PAY_IS pay(NftWallet.BILL_PAY_IC request) {

        //token verify
        WalletToken walletToken = WalletToken.decode(request.getToken());
        if(!walletToken.verify(publicKey)){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN);
        }

        WalletOrderBizContent bizContent = walletToken.getBizContent();

        //exist check
        WalletBill walletBill = walletBillDao.findOne(WalletBillQuery.newBuilder()
                .uid(request.getUid())
                .productCode(bizContent.getProductCode())
                .outOrderId(bizContent.getOutOrderId()).build()
        );
        if(null == walletBill){
            walletBill = new WalletBill();
            walletBill.setActivityCfgId(bizContent.getActivityCfgId());
            walletBill.setUid(request.getUid());
            walletBill.setProductCode(bizContent.getProductCode());
            walletBill.setProductId(bizContent.getProductId());
            walletBill.setOutOrderId(bizContent.getOutOrderId());
            walletBill.setMerchantType(bizContent.getMerchantType());
            walletBill.setMerchantId(bizContent.getMerchantId());
            walletBill.setExpiredAt(bizContent.getExpiredAt());
            walletBill.setCurrency(bizContent.getCurrency());
            walletBill.setValue(bizContent.getValue());
            walletBill.setCreatedAt(System.currentTimeMillis());
            walletBill.setUpdatedAt(walletBill.getCreatedAt());
            walletBill.setState(WalletBillState.CREATE.name());
            walletBill.setRemark(bizContent.getRemark());
            walletBillDao.insert(walletBill);
        }

        WalletBillState currentState = WalletBillState.valueOf(walletBill.getState());
        if(WalletBillState.PAYED.equals(currentState) || WalletBillState.FAIL.equals(currentState)){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN);
        }

        try{
            //password check
            walletSettingService.checkPassword(request.getUid(), request.getPassword());
            //verify balance
            walletService.checkBalance(request.getUid(), bizContent.getCurrency(), bizContent.getValue());
        }catch (Exception e){
            routeClient.send(
                    NftExchange.PAY_RECEIPT_PUSH_IC.newBuilder()
                            .setAssetsId(Long.valueOf(bizContent.getProductId()))
                            .setOrderId(bizContent.getOutOrderId())
                            .setState(WalletBillState.FAIL.name())
                            .build()
                    , BuyReceiptPushRouteRequest.class);
            changeState(walletBill, WalletBillState.FAIL, "verify error");
            throw e;
        }

        //Require biz to make confirm of this order
        NftExchange.PAY_RECEIPT_PUSH_IS result = null;
        try{
            result = routeClient.send(
                    NftExchange.PAY_RECEIPT_PUSH_IC.newBuilder()
                            .setAssetsId(Long.valueOf(bizContent.getProductId()))
                            .setOrderId(bizContent.getOutOrderId())
                            .setState(WalletBillState.PAYED.name())
                            .build(),
                    BuyReceiptPushRouteRequest.class
            );
        }catch (Exception e){
            changeState(walletBill, WalletBillState.RETRY, e.getMessage());
            throw e;
        }

        if(result.getOk()){
            //do pay
            walletService.decBalance(walletBill.getUid(), walletBill.getCurrency(), walletBill.getValue());
            changeState(walletBill, WalletBillState.PAYED, "ok");
        }else{
            changeState(walletBill, WalletBillState.FAIL, "biz error");
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_BIZ_VERIFY_FAILED);
        }

        return NftWallet.BILL_PAY_IS.newBuilder()
                .setBill(detail(NftWallet.BILL_DETAIL_IC.newBuilder()
                        .setUid(walletBill.getUid())
                        .setProductCode(walletBill.getProductCode())
                        .setOutOrderId(walletBill.getOutOrderId())
                        .build()).getBills())
                .build();

    }


    public WalletBillDTO detail(BillDetailRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        WalletBill walletBill = walletBillDao.findOne(
                WalletBillQuery.newBuilder().uid(uid).id(request.getBillId()).build()
        );
        WalletBillDTO walletBillDTO = WalletBillDTO.from(walletBill);
        if(null != walletBill.getMerchantId()){
            walletBillDTO.setUidProfile(
                    NftUserProfileDTO.from(
                            routeClient.send(
                                    Search.SEARCH_USER_PROFILE_IC.newBuilder()
                                            .addUids(walletBill.getMerchantId())
                                            .build(),
                                    SearchUserProfileRouteRequest.class
                            ).getProfiles(0)
                    )
            );
        }
        if(WalletProductCode.NFT.name().equals(walletBill.getProductCode())){
            walletBillDTO.setProductName(
                    routeClient.send(
                            NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                                    .setId(Long.valueOf(walletBill.getProductId()))
                                    .build(),
                            AssetsDetailRouteRequest.class
                    ).getAssets().getName()
            );
        }

        WalletActivityTemplate walletActivityTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_activity);
        if(null != walletActivityTemplate){
            WalletActivityConfig walletActivityConfig = walletActivityTemplate.findOne(walletBill.getActivityCfgId());
            if(null != walletActivityConfig){
                walletBillDTO.setType(walletActivityConfig.getType());
            }
        }

        return walletBillDTO;
    }


    public NftWallet.BILL_DETAIL_IS detail(NftWallet.BILL_DETAIL_IC request) {

        WalletBill walletBill = walletBillDao.findOne(
                WalletBillQuery.newBuilder().uid(request.getUid()).productCode(request.getProductCode()).outOrderId(request.getOutOrderId()).build()
        );
        NftWallet.BillDTO billDTO = WalletBillDTO.toProto(walletBill);
        return NftWallet.BILL_DETAIL_IS.newBuilder()
                .setBills(billDTO)
                .build();
    }

    public Page<WalletBillSimpleDTO> list(BillListRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        return new Page<WalletBillSimpleDTO>(
                0,
                walletBillDao.findPage(
                        WalletBillQuery.newBuilder()
                                .uid(uid)
                                .setPage(request.getPage())
                                .setPageSize(request.getPageSize())
                                .setSortField("_id")
                                .setReverse(true)
                                .build()
                ).getData().stream().map(bill -> {
                    WalletBillSimpleDTO walletBillSimpleDTO = new WalletBillSimpleDTO();

                    WalletActivityTemplate walletActivityTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_activity);
                    if(null != walletActivityTemplate){
                        WalletActivityConfig walletActivityConfig = walletActivityTemplate.findOne(bill.getActivityCfgId());
                        if(null != walletActivityConfig){
                            walletBillSimpleDTO.setTitle(walletActivityConfig.getName());
                            walletBillSimpleDTO.setIcon(walletActivityConfig.getIcon());
                        }
                    }

                    walletBillSimpleDTO.setId(bill.getId());
                    walletBillSimpleDTO.setCreatedAt(bill.getCreatedAt());
                    walletBillSimpleDTO.setCurrency(bill.getCurrency());
                    walletBillSimpleDTO.setValue(bill.getValue());
                    return walletBillSimpleDTO;
                }).collect(Collectors.toList())
        );

    }

    private void changeState(WalletBill walletBill, WalletBillState state, String remark) {
        walletBillDao.update(
                WalletBillQuery.newBuilder().uid(walletBill.getUid()).id(walletBill.getId()).build(),
                WalletBillUpdate.newBuilder()
                        .setState(state.name())
                        .setRemark(remark)
                        .build()
        );
    }

    public NftWallet.RECHARGE_IS recharge(NftWallet.RECHARGE_IC request) {

        //token verify
        WalletToken walletToken = WalletToken.decode(request.getToken());
        if(!walletToken.verify(publicKey)){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN);
        }

        WalletOrderBizContent bizContent = walletToken.getBizContent();
        //exist check
        WalletBill walletBill = walletBillDao.findOne(WalletBillQuery.newBuilder()
                .uid(request.getUid())
                .productCode(bizContent.getProductCode())
                .outOrderId(bizContent.getOutOrderId()).build()
        );
        if(null == walletBill){
            walletBill = new WalletBill();
            walletBill.setActivityCfgId(bizContent.getActivityCfgId());
            walletBill.setUid(request.getUid());
            walletBill.setProductCode(bizContent.getProductCode());
            walletBill.setProductId(bizContent.getProductId());
            walletBill.setOutOrderId(bizContent.getOutOrderId());
            walletBill.setMerchantType(bizContent.getMerchantType());
            walletBill.setMerchantId(bizContent.getMerchantId());
            walletBill.setExpiredAt(bizContent.getExpiredAt());
            walletBill.setCurrency(bizContent.getCurrency());
            walletBill.setValue(bizContent.getValue());
            walletBill.setCreatedAt(System.currentTimeMillis());
            walletBill.setUpdatedAt(walletBill.getCreatedAt());
            walletBill.setState(WalletBillState.CREATE.name());
            walletBill.setRemark(bizContent.getRemark());
            walletBillDao.insert(walletBill);
        }

        WalletBillState currentState = WalletBillState.valueOf(walletBill.getState());
        if(WalletBillState.PAYED.equals(currentState) || WalletBillState.FAIL.equals(currentState)){
            throw BizException.newInstance(NftExchangeErrorCodes.WALLET_PAY_EXCEPTION_UNCORRECT_PAY_TOKEN);
        }

        //do pay
        walletService.incBalance(walletBill.getUid(), walletBill.getCurrency(), walletBill.getValue());
        changeState(walletBill, WalletBillState.PAYED, "");

        return NftWallet.RECHARGE_IS.newBuilder()
                .setBill(detail(NftWallet.BILL_DETAIL_IC.newBuilder()
                        .setUid(walletBill.getUid())
                        .setProductCode(walletBill.getProductCode())
                        .setOutOrderId(walletBill.getOutOrderId())
                        .build()).getBills())
                .build();

    }
}
