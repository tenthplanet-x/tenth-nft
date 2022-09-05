package com.tenth.nft.exchange.web3.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.NftIdModule;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.WalletMerchantType;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.WalletOrderType;
import com.tenth.nft.convention.wallet.WalletProductCode;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.convention.web3.sign.OfferDataForSign;
import com.tenth.nft.exchange.buildin.dto.NftOfferDTO;
import com.tenth.nft.exchange.common.service.NftBelongService;
import com.tenth.nft.exchange.common.service.NftOfferFlowService;
import com.tenth.nft.exchange.web3.vo.*;
import com.tenth.nft.exchange.web3.wallet.Web3WalletProvider;
import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shijie
 */
@Service
public class Web3OfferFlowService extends AbsSignService {

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftOfferFlowService nftOfferFlowService;
    @Autowired
    private NftBelongService nftBelongService;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private Web3WalletProvider web3WalletProvider;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private Web3OrderService web3OrderService;

    public Web3OfferCreateResponse createOffer(Web3OfferCreateRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(request.getAssetsId())
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();
        if(assetsDTO.getSupply() < request.getQuantity()){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INVALID_PARAMS);
        }
        if(Times.isExpired(request.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INVALID_PARAMS);
        }
        int owns = 0;
        NftBelong nftBelong = nftBelongService.findOne(request.getAssetsId(), uid);
        if(null != nftBelong){
            owns = nftBelong.getQuantity();
        }
        if(owns == assetsDTO.getSupply()){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_BELONGS_TO_YOU);
        }

        OfferDataForSign offerDataForSign = new OfferDataForSign();
        offerDataForSign.setAssetsId(request.getAssetsId());
        offerDataForSign.setPrice(request.getPrice());
        offerDataForSign.setQuantity(request.getQuantity());
        offerDataForSign.setExpireAt(request.getExpireAt());
        offerDataForSign.setDomain(tpulseContractHelper.getDomain());
        String dataForSign = offerDataForSign.toDataForSignBase64();
        String from = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        String token = wrap(request);

        return new Web3OfferCreateResponse(from, dataForSign, token);

    }


    public NftOfferDTO confirmOffer(Web3OfferConfirmRequest _request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String uidAddress = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setNeedBalance(false)
                        .setUid(uid)
                        .build(),
            Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        Web3OfferCreateRequest request = unwrap(_request.getContent(), Web3OfferCreateRequest.class);
        NftOffer nftOffer = new NftOffer();
        nftOffer.setAssetsId(request.getAssetsId());
        nftOffer.setUid(uid);
        nftOffer.setUidAddress(uidAddress);
        nftOffer.setUidSignature(_request.getSignature());
        nftOffer.setQuantity(request.getQuantity());
        nftOffer.setPrice(request.getPrice());
        nftOffer.setCurrency(request.getCurrency());
        nftOffer.setCreatedAt(System.currentTimeMillis());
        nftOffer.setUpdatedAt(nftOffer.getCreatedAt());
        nftOffer.setExpireAt(request.getExpireAt());

        //Save the creator and the rate of create fee
        //creator fee rate
        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(nftOffer.getAssetsId())
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();
        if(!Strings.isNullOrEmpty(assetsDTO.getCreatorFeeRate())){
            nftOffer.setCreatorUid(assetsDTO.getCreator());
            nftOffer.setCreatorFeeRate(assetsDTO.getCreatorFeeRate());
            nftOffer.setCreatorAddress(assetsDTO.getCreatorAddress());
        }

        nftOfferFlowService.create(nftOffer);

        return NftOfferDTO.convertFrom(nftOffer);

    }

    public Web3AcceptCreateResponse createAccept(Web3AcceptCreateRequest request) {

        //WalletOrderBizContent.newBuilder()
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        String from = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
          Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        NftOffer nftOffer = nftOfferFlowService.findOne(request.getAssetsId(), request.getOfferId());
        String currency = nftOffer.getCurrency();
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        String blockchain = walletCurrencyTemplate.findOne(currency).getBlockchain();

        Long orderId = createOrderId();
        Long expiredAt = WalletTimes.getExpiredAt();
        //WalletOrderBizContent
        WalletOrderBizContent walletOrder = WalletOrderBizContent.newBuilder()
                .activityCfgId(WalletOrderType.NftIncome.getActivityCfgId())
                .productCode(WalletProductCode.NFT_OFFER.name())
                .productId(String.valueOf(nftOffer.getAssetsId()))
                .outOrderId(String.valueOf(orderId))
                .merchantType(WalletMerchantType.PERSONAL.name())
                .merchantId(String.valueOf(nftOffer.getUid()))
                .currency(nftOffer.getCurrency())
                .value(nftOffer.getPrice())
                .expiredAt(expiredAt)
                .remark("")
                .profits(createProfits(nftOffer))
                .build();
        String token = web3WalletProvider.createToken(walletOrder);

        //create transaction params
        //get walletAddress
        String txnData = tpulseContractHelper.createAcceptTransactionData(
                nftOffer.getUidAddress(),
                nftOffer.getAssetsId(),
                nftOffer.getQuantity(),
                nftOffer.getPrice(),
                nftOffer.getExpireAt(),
                nftOffer.getUidSignature()
        );
        String txnValue = BigDecimal.ZERO.toString();
        String txnTo = tpulseContractHelper.getContractAddress();

        //create payment order
        NftOrder nftOrder = new NftOrder();
        nftOrder.setId(orderId);
        nftOrder.setBuyer(nftOffer.getUid());
        nftOrder.setOwner(uid);
        nftOrder.setAssetsId(nftOffer.getAssetsId());
        nftOrder.setOfferId(nftOffer.getId());
        nftOrder.setCreatedAt(System.currentTimeMillis());
        nftOrder.setUpdatedAt(nftOrder.getCreatedAt());
        nftOrder.setCurrency(nftOffer.getCurrency());
        nftOrder.setPrice(nftOffer.getPrice());
        nftOrder.setQuantity(nftOffer.getQuantity());
        nftOrder.setStatus(NftOrderStatus.CREATE);
        nftOrder.setExpiredAt(expiredAt);
        web3OrderService.create(nftOrder);

        Web3AcceptCreateResponse response = new Web3AcceptCreateResponse();
        response.setContent(token);
        response.setFrom(from);
        response.setTxnTo(txnTo);
        response.setTxnValue(txnValue);
        response.setTxnData(txnData);
        return response;
    }

    protected Long createOrderId() {
        return gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
    }

    protected List<WalletOrderBizContent.Profit> createProfits(NftOffer nftOffer) {

        List<WalletOrderBizContent.Profit> profits = new ArrayList<>();

        BigDecimal profitValue = new BigDecimal(nftOffer.getPrice());
        BigDecimal creatorFee = BigDecimal.ZERO;
        if(!Strings.isNullOrEmpty(nftOffer.getCreatorFeeRate())){
            creatorFee = profitValue.multiply(new BigDecimal(nftOffer.getCreatorFeeRate()).divide(new BigDecimal(100)));
        }
        //buyer
        {
            WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
            profit.setActivityCfgId(WalletOrderType.NftExpense.getActivityCfgId());
            profit.setCurrency(nftOffer.getCurrency());
            profit.setValue(nftOffer.getPrice());
            profit.setTo(nftOffer.getUid());
            profit.setToAddress(nftOffer.getUidAddress());
            profits.add(profit);
        }
        //creator fee
        {
            if(creatorFee.compareTo(BigDecimal.ZERO) > 0){
                WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
                profit.setActivityCfgId(WalletOrderType.CreatorIncome.getActivityCfgId());
                profit.setCurrency(nftOffer.getCurrency());
                profit.setValue(creatorFee.toString());
                profit.setTo(nftOffer.getCreatorUid());
                profit.setToAddress(nftOffer.getCreatorAddress());
                profits.add(profit);
            }
        }
        //service fee

        return profits;

    }
}
