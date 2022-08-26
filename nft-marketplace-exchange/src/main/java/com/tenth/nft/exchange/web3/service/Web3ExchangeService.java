package com.tenth.nft.exchange.web3.service;

import com.google.common.base.Strings;
import com.qiniu.util.Json;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.NftIdModule;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.exchange.*;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.templates.*;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.exchange.buildin.service.BuildInExchangeService;
import com.tenth.nft.exchange.common.service.NftBelongService;
import com.tenth.nft.exchange.common.service.NftListingService;
import com.tenth.nft.exchange.common.service.AbsExchangeService;
import com.tenth.nft.exchange.common.service.NftExchangeEventService;
import com.tenth.nft.exchange.web3.dto.ListingCreateResponse;
import com.tenth.nft.exchange.web3.dto.ListingDataForSign;
import com.tenth.nft.exchange.web3.dto.PaymentCheckResponse;
import com.tenth.nft.exchange.web3.dto.PaymentCreateResponse;
import com.tenth.nft.exchange.web3.vo.*;
import com.tenth.nft.exchange.web3.wallet.Web3WalletProvider;
import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderUpdate;
import com.tenth.nft.orm.marketplace.entity.NftBelong;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.orm.marketplace.entity.NftOrder;
import com.tenth.nft.orm.marketplace.entity.NftOrderStatus;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.cypher.rsa.RSAUtils;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
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
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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
    private Web3WalletProvider web3WalletProvider;
    @Autowired
    private NftBelongService nftBelongService;
    @Autowired
    private Web3Properties web3Properties;

    /**
     * Create listing data for sign for web3 authentication
     * @param request
     * @return
     */
    public ListingCreateResponse createListing(Web3ExchangeListingAuthRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        NftWeb3Exchange.WEB3_LISTING_CREATE_IS routeResponse = routeClient.send(
                NftWeb3Exchange.WEB3_LISTING_CREATE_IC.newBuilder()
                        .setUid(uid)
                        .setAssetsId(request.getAssetsId())
                        .setCurrency(request.getCurrency())
                        .setPrice(request.getPrice())
                        .setQuantity(request.getQuantity())
                        .setStartAt(request.getStartAt())
                        .setExpireAt(request.getExpireAt())
                        .build(),
                Web3ListingCreateRouteRequest.class
        );

        ListingCreateResponse createResponse = new ListingCreateResponse();
        createResponse.setDataForSign(routeResponse.getDataForSign());
        createResponse.setToken(routeResponse.getToken());

        String address = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        createResponse.setFrom(address);

        return createResponse;

    }

    public NftWeb3Exchange.WEB3_LISTING_CREATE_IS createListing(NftWeb3Exchange.WEB3_LISTING_CREATE_IC request) throws Exception{

        if(Times.isExpired(request.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }
        //Quantity check
        NftBelong nftBelong = nftBelongService.findOne(request.getAssetsId(), request.getUid());
        if(null == nftBelong || nftBelong.getQuantity() < request.getQuantity()){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }
        //Check the blockchain is correct
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        String blockchain = walletCurrencyTemplate.findOne(request.getCurrency()).getBlockchain();
        if(!web3Properties.getBlockchain().equals(blockchain)){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }
        //Get user web3 address
        String address = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(request.getUid())
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        ListingDataForSign dataForSign = new ListingDataForSign();
        dataForSign.setSeller(address);
        dataForSign.setDomain(tpulseContractHelper.getDomain());
        dataForSign.setAssetsId(request.getAssetsId());
        dataForSign.setQuantity(request.getQuantity());
        dataForSign.setPrice(request.getPrice());
        dataForSign.setCurrency(request.getCurrency());
        dataForSign.setStartAt(request.getStartAt());
        dataForSign.setExpireAt(request.getExpireAt());
        String content = Base64Utils.encode(JsonUtil.toJson(dataForSign.toDataForSign()).getBytes(StandardCharsets.UTF_8));
        String signature = signContent(content);
        String token = wrapToToken(JsonUtil.toJson(dataForSign), signature);

        return NftWeb3Exchange.WEB3_LISTING_CREATE_IS.newBuilder()
                .setDataForSign(content)
                .setToken(token)
                .build();
    }

    private String wrapToToken(String content, String signature) {
        String wrappedStr = String.format("%s.%s", Base64Utils.encode(content.getBytes(StandardCharsets.UTF_8)), signature);
        return wrappedStr;
    }

    private String unwrapToken(String token) {
        String content = new String(Base64Utils.decode(token.substring(0, token.lastIndexOf("."))), StandardCharsets.UTF_8);
        return content;
    }

    private String signContent(String content) throws Exception {
        return Base64Utils.encode(RSAUtils.sign(content.getBytes(StandardCharsets.UTF_8), web3Properties.getRsa().getPrivateKey()));
    }



    /**
     * Verify and create listing data
     * @param request
     */
    public void confirmListing(Web3ExchangeListingConfirmRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        routeClient.send(
                NftWeb3Exchange.WEB3_LISTING_CONFIRM_IC.newBuilder()
                        .setUid(uid)
                        .setToken(request.getToken())
                        .setSignature(request.getSignature())
                        .build(),
                Web3ListingConfirmRouteRequest.class
        );

    }

    public NftWeb3Exchange.WEB3_LISTING_CONFIRM_IS confirmListing(NftWeb3Exchange.WEB3_LISTING_CONFIRM_IC request){

        String content = unwrapToken(request.getToken());

        ListingDataForSign listingDataForSign = JsonUtil.fromJson(content, ListingDataForSign.class);

        NftListing nftListing = new NftListing();
        nftListing.setUid(request.getUid());

        String uidAddress = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(request.getUid())
                        .setNeedBalance(false)
                        .build(),
            Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        nftListing.setUidAddress(uidAddress);
        nftListing.setAssetsId(listingDataForSign.getAssetsId());
        nftListing.setQuantity(listingDataForSign.getQuantity());
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
            nftListing.setCreatorAddress(assetsDTO.getCreatorAddress());
        }

        Long activityId = nftExchangeEventService.sendListingEvent(nftListing);
        nftListing.setActivityId(activityId);
        nftListing = nftListingService.insert(nftListing);

        nftExchangeEventService.sendListingEvent(nftListing);

        return NftWeb3Exchange.WEB3_LISTING_CONFIRM_IS.newBuilder().build();
    }



    /**
     * Create payment transaction
     * @param request
     * @return
     */
    public PaymentCreateResponse createPayment(Web3PaymentCreateRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String uidAddress = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();

        NftWeb3Exchange.WEB3_PAYMENT_CREATE_IS routeResponse = routeClient.send(
                NftWeb3Exchange.WEB3_PAYMENT_CREATE_IC.newBuilder()
                        .setUid(uid)
                        .setAssetsId(request.getAssetsId())
                        .setListingId(request.getListingId())
                        .build(),
                Web3PaymentCreateRouteRequest.class
        );

        return new PaymentCreateResponse(
                routeResponse.getToken(),
                routeResponse.getTxnData(),
                routeResponse.getTxnValue(),
                routeResponse.getTxnTo(),
                uidAddress
        );
    }

    public NftWeb3Exchange.WEB3_PAYMENT_CREATE_IS createPayment(NftWeb3Exchange.WEB3_PAYMENT_CREATE_IC request){

        NftListing nftListing = nftListingService.findOne(request.getAssetsId(), request.getListingId());
        checkPaymentState(request.getUid(), nftListing);

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
                nftListing.getUidAddress(),
                nftListing.getAssetsId(),
                nftListing.getQuantity(),
                nftListing.getPrice(),
                nftListing.getSignature()
        );
        String txnValue = Convert.toWei(nftListing.getPrice(), Convert.Unit.ETHER).toBigInteger().toString();
        String txnTo = tpulseContractHelper.getContractAddress();

        //create payment order
        createPaymentOrder(orderId, expiredAt, request.getUid(), nftListing);

        return NftWeb3Exchange.WEB3_PAYMENT_CREATE_IS.newBuilder()
                .setToken(token)
                .setTxnTo(txnTo)
                .setTxnData(txnData)
                .setTxnValue(txnValue)
                .build();
    }

    public NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS confirmPayment(NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IC request) {

        NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS.Builder builder = NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IS.newBuilder();

        Long orderId = Long.valueOf(request.getOrderId());
        SimpleQuery orderQuery = NftOrderQuery.newBuilder().assetsId(request.getAssetsId()).id(orderId).build();
        NftOrder nftOrder = nftOrderDao.findOne(orderQuery);
        if(null != nftOrder && NftOrderStatus.CREATE.equals(nftOrder.getStatus())){
            WalletBillState state = WalletBillState.valueOf(request.getState());
            switch (state){
                case PAYED:
                    nftOrderDao.update(
                            NftOrderQuery.newBuilder().assetsId(nftOrder.getAssetsId()).id(nftOrder.getId()).build(),
                            NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.COMPLETE).txn(request.getTxn()).build()
                    );
                    nftListingService.remove(nftOrder.getAssetsId(), nftOrder.getListingId());
                    //owner listing refresh
                    nftListingService.refreshListingsBelongTo(nftOrder.getOwner(), nftOrder.getAssetsId());
                    break;
                case FAIL:
                    nftOrderDao.update(
                            orderQuery,
                            NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.CANCEL).remark("pay failed").build()
                    );
                    break;
                default:
            }
        }

        return builder.build();
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
        String token = web3WalletProvider.createToken(walletOrder);
        return token;
    }

    protected Long createOrderId() {
        return gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
    }

    protected void checkPaymentState(Long uid, NftListing nftListing) {
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
            profit.setToAddress(nftListing.getUidAddress());
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
                profit.setToAddress(nftListing.getCreatorAddress());
                profits.add(profit);
            }
        }
        //service fee

        return profits;

    }


}
