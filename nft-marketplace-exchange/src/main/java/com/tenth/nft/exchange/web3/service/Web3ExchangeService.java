package com.tenth.nft.exchange.web3.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.NftIdModule;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.exchange.buildin.service.BuildInExchangeService;
import com.tenth.nft.exchange.common.service.NftListingService;
import com.tenth.nft.exchange.common.wallet.IWalletProvider;
import com.tenth.nft.exchange.common.wallet.WalletProviderFactory;
import com.tenth.nft.exchange.common.service.AbsExchangeService;
import com.tenth.nft.exchange.common.service.NftExchangeEventService;
import com.tenth.nft.exchange.web3.dto.ListingCreateResponse;
import com.tenth.nft.exchange.web3.dto.ListingDataForSign;
import com.tenth.nft.exchange.web3.dto.PaymentCheckResponse;
import com.tenth.nft.exchange.web3.dto.PaymentCreateResponse;
import com.tenth.nft.exchange.web3.vo.*;
import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderUpdate;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.orm.marketplace.entity.NftOrder;
import com.tenth.nft.orm.marketplace.entity.NftOrderStatus;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.json.JsonUtil;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author shijie
 */
@Service
public class Web3ExchangeService extends AbsExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Web3ExchangeService.class);

    @Autowired
    private NftListingService nftListingService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftExchangeEventService nftExchangeEventService;
    @Autowired
    private BuildInExchangeService nftExchangeService;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private NftOrderDao nftOrderDao;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private WalletProviderFactory walletProviderFactory;

    /**
     * Create listing data for sign for web3 authentication
     * @param request
     * @return
     */
    public ListingCreateResponse createListing(Web3ExchangeListingAuthRequest request) {
        throw new UnsupportedOperationException();
    }

    public NftWeb3Exchange.WEB3_LISTING_CREATE_IS createListing(NftWeb3Exchange.WEB3_LISTING_CREATE_IC request){

        ListingDataForSign dataForSign = new ListingDataForSign();
        dataForSign.setAssetsId(request.getAssetsId());
        dataForSign.setPrice(request.getPrice());
        dataForSign.setCurrency(request.getCurrency());
        dataForSign.setStartAt(request.getStartAt());
        dataForSign.setExpireAt(request.getExpireAt());
        dataForSign.setMethod("mint");
        String content = JsonUtil.toJson(dataForSign);
        return NftWeb3Exchange.WEB3_LISTING_CREATE_IS.newBuilder()
                .setDataForSign(content)
                .setToken("")
                .build();
    }

    /**
     * Verify and create listing data
     * @param request
     */
    public void confirmListing(Web3ExchangeListingConfirmRequest request) {
        throw new UnsupportedOperationException();
    }

    public void confirmListing(NftWeb3Exchange.WEB3_LISTING_CONFIRM_IC request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        ListingDataForSign listingDataForSign = JsonUtil.fromJson(request.getDataForSign(), ListingDataForSign.class);

        NftListing nftListing = new NftListing();
        nftListing.setUid(uid);
        nftListing.setAssetsId(listingDataForSign.getAssetsId());
        nftListing.setCurrency(listingDataForSign.getCurrency());
        nftListing.setPrice(listingDataForSign.getPrice());
        nftListing.setStartAt(listingDataForSign.getStartAt());
        nftListing.setExpireAt(listingDataForSign.getExpireAt());
        nftListing.setCreatedAt(System.currentTimeMillis());
        nftListing.setUpdatedAt(nftListing.getCreatedAt());

        //creator fee rate
        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(nftListing.getAssetsId())
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();
        if(!Strings.isNullOrEmpty(assetsDTO.getCreatorFeeRate())){
            nftListing.setCreatorUid(assetsDTO.getCreator());
            nftListing.setCreatorFeeRate(assetsDTO.getCreatorFeeRate());
        }

        Long activityId = nftExchangeEventService.sendListingEvent(nftListing);
        nftListing.setActivityId(activityId);
        nftListing = nftListingService.insert(nftListing);

        nftExchangeEventService.sendListingRouteEvent(nftListing.getAssetsId());

    }

    /**
     * Create payment transaction
     * @param request
     * @return
     */
    public PaymentCreateResponse createPayment(Web3PaymentCreateRequest request) {
        throw new UnsupportedOperationException();
    }

    public NftWeb3Exchange.WEB3_PAYMENT_CREATE_IS createPayment(NftWeb3Exchange.WEB3_PAYMENT_CREATE_IC request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        NftListing nftListing = nftListingService.findOne(request.getAssetsId(), request.getListingId());
        getPaymentState(uid, nftListing);

        String currency = nftListing.getCurrency();
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        String blockchain = walletCurrencyTemplate.findOne(currency).getBlockchain();

        Long orderId = createOrderId();
        Long expiredAt = WalletTimes.getExpiredAt();

        //WalletOrderBizContent
        WalletOrderBizContent walletOrder = WalletOrderBizContent.newBuilder()
                .activityCfgId(WalletOrderType.NftExpense.getActivityCfgId())
                .productCode(WalletProductCode.NFT.name())
                .productId(String.valueOf(nftListing.getAssetsId()))
                .outOrderId(String.valueOf(orderId))
                .merchantType(WalletMerchantType.PERSONAL.name())
                .merchantId(String.valueOf(nftListing.getUid()))
                .currency(nftListing.getCurrency())
                .value(nftListing.getPrice())
                .expiredAt(expiredAt)
                .remark("")
                .profits(createProfits(nftListing))
                .build();
        String token = createPaymentToken(blockchain, walletOrder);

        //create transaction params
        //get walletAddress
        String txnData = tpulseContractHelper.createPaymentTransactionData(
                nftListing.getPayWalletAddress(),
                nftListing.getAssetsId(),
                nftListing.getQuantity(),
                nftListing.getPrice(),
                nftListing.getSignature()
        );
        String txnValue = nftListing.getPrice();
        String txnTo = tpulseContractHelper.getContractAddress();

        //create payment order
        createPaymentOrder(orderId, expiredAt, uid, nftListing);

        return NftWeb3Exchange.WEB3_PAYMENT_CREATE_IS.newBuilder()
                .setToken(token)
                .setTxnTo(txnTo)
                .setTxnData(txnData)
                .setTxnValue(txnValue)
                .build();
    }

    public void confirmPayment(Web3PaymentConfirmRequest request) {
        throw new UnsupportedOperationException();
    }

    public NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS confirmPayment(NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IC request) {
        //throw new UnsupportedOperationException();
        NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS.Builder builder = NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS.newBuilder();

        Long orderId = Long.valueOf(request.getOrderId());
        SimpleQuery orderQuery = NftOrderQuery.newBuilder().assetsId(request.getAssetsId()).id(orderId).build();
        NftOrder nftOrder = nftOrderDao.findOne(orderQuery);
        if(null != nftOrder && NftOrderStatus.CREATE.equals(nftOrder.getStatus())){
            WalletBillState state = WalletBillState.valueOf(request.getState());
            switch (state){
                case PAYED:
                    try {
                        listingBuyConfirm(nftOrder);
                        builder.setOk(true);
                    }catch (BizException e){
                        LOGGER.error("", e);
                        builder.setOk(false);
                    }
                    break;
                case FAIL:
                    nftOrderDao.update(
                            orderQuery,
                            NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.CANCEL).remark("pay failed").build()
                    );
                    builder.setOk(false);
                    break;
                default:
                    builder.setOk(true);
            }
            return builder.build();
        }else{
            return builder.setOk(false).build();
        }

    }

    private void listingBuyConfirm(NftOrder nftOrder) {

        nftOrder.setStatus(NftOrderStatus.COMPLETE);
        nftOrderDao.update(
                NftOrderQuery.newBuilder().assetsId(nftOrder.getAssetsId()).id(nftOrder.getId()).build(),
                NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.COMPLETE).build()
        );

        nftListingService.remove(nftOrder.getAssetsId(), nftOrder.getListingId());
        //owner listing refresh
        nftListingService.refreshListingsBelongTo(nftOrder.getOwner(), nftOrder.getAssetsId());
    }

    public PaymentCheckResponse getPaymentState(Web3PaymentCheckRequest request) {
        throw new UnsupportedOperationException();
    }

    private void createPaymentOrder(Long orderId, Long expiredAt, Long buyer, NftListing nftListing) {
        NftOrder nftOrder = new NftOrder();
        nftOrder.setId(orderId);
        nftOrder.setBuyer(buyer);
        nftOrder.setOwner(nftListing.getUid());
        nftOrder.setAssetsId(nftListing.getAssetsId());
        nftOrder.setListingId(nftListing.getId());
        nftOrder.setCreatedAt(System.currentTimeMillis());
        nftOrder.setUpdatedAt(nftOrder.getCreatedAt());
        nftOrder.setCurrency(nftListing.getCurrency());
        nftOrder.setPrice(nftListing.getPrice());
        nftOrder.setQuantity(nftListing.getQuantity());
        nftOrder.setStatus(NftOrderStatus.CREATE);
        nftOrder.setExpiredAt(expiredAt);
        nftOrderDao.insert(nftOrder);
    }

    protected String createPaymentToken(String blockchain, WalletOrderBizContent walletOrder) {
        IWalletProvider walletProvider = walletProviderFactory.getByCurrency(blockchain);
        String token = walletProvider.createToken(walletOrder);
        return token;
    }

    protected Long createOrderId() {
        return gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
    }

    protected void getPaymentState(Long uid, NftListing nftListing) {
        if(null == nftListing || nftListing.getCanceled()){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_EXIST);
        }
        if(Times.earlierThan(nftListing.getStartAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NOT_START);
        }
        if(Times.isExpired(nftListing.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_EXPIRED);
        }
        if(nftListing.getUid().equals(uid)){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_BELONGS_TO_YOU);
        }
    }

    protected List<WalletOrderBizContent.Profit> createProfits(NftListing nftListing) {

        List<WalletOrderBizContent.Profit> profits = new ArrayList<>();

        BigDecimal profitValue = new BigDecimal(nftListing.getPrice());
        BigDecimal creatorFee = BigDecimal.ZERO;
        if(!Strings.isNullOrEmpty(nftListing.getCreatorFeeRate())){
            creatorFee = profitValue.multiply(new BigDecimal(nftListing.getCreatorFeeRate()).divide(new BigDecimal(100)));
            profitValue = profitValue.subtract(creatorFee);
        }
        //seller
        {
            WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
            profit.setActivityCfgId(WalletOrderType.NftIncome.getActivityCfgId());
            profit.setCurrency(nftListing.getCurrency());
            profit.setValue(profitValue.toString());
            profit.setTo(nftListing.getUid());
            profits.add(profit);
        }
        //creator fee
        {
            if(creatorFee.compareTo(BigDecimal.ZERO) > 0){
                WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
                profit.setActivityCfgId(WalletOrderType.CreatorIncome.getActivityCfgId());
                profit.setCurrency(nftListing.getCurrency());
                profit.setValue(creatorFee.toString());
                profit.setTo(nftListing.getCreatorUid());
                profits.add(profit);
            }
        }
        //service fee

        return profits;

    }


}
