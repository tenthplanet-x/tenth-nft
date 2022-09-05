package com.tenth.nft.exchange.web3.service;

import com.google.common.base.Strings;
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
import com.tenth.nft.convention.web3.sign.StructContentHash;
import com.tenth.nft.convention.web3.utils.TxnStatus;
import com.tenth.nft.exchange.common.service.*;
import com.tenth.nft.exchange.web3.dto.ListingCreateResponse;
import com.tenth.nft.exchange.web3.dto.ListingDataForSign;
import com.tenth.nft.exchange.web3.dto.PaymentCheckResponse;
import com.tenth.nft.exchange.web3.dto.PaymentCreateResponse;
import com.tenth.nft.exchange.web3.vo.*;
import com.tenth.nft.exchange.web3.wallet.Web3WalletProvider;
import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.ContractTransactionReceipt;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.cypher.rsa.RSAUtils;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
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
public class Web3ListingService extends AbsExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Web3ListingService.class);

    @Autowired
    private NftListingService nftListingService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftExchangeEventService nftExchangeEventService;
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
    @Autowired
    private Web3OfferFlowService web3OfferFlowService;
    @Autowired
    private NftOfferFlowService nftOfferFlowService;

    /**
     * Create listing data for sign for web3 authentication
     * @param request
     * @return
     */
    public ListingCreateResponse createListing(Web3ExchangeListingAuthRequest request) throws Exception {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        preListingCheck(
                uid,
                request.getAssetsId(),
                request.getQuantity(),
                request.getCurrency(),
                request.getPrice(),
                request.getExpireAt()
        );

        ListingDataForSign dataForSign = new ListingDataForSign();
        //Get user web3 address
        String sellerAddress = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        dataForSign.setSeller(sellerAddress);
        dataForSign.setDomain(tpulseContractHelper.getDomain());
        dataForSign.setAssetsId(request.getAssetsId());
        dataForSign.setQuantity(request.getQuantity());
        dataForSign.setPrice(request.getPrice());
        dataForSign.setCurrency(request.getCurrency());
        dataForSign.setStartAt(request.getStartAt());
        dataForSign.setExpireAt(request.getExpireAt());
        String content = JsonUtil.toJson(dataForSign.toDataForSign());
        String token = StructContentHash.wrap(JsonUtil.toJson(dataForSign.toDataForSign()), web3Properties.getRsa().getPrivateKey());

        ListingCreateResponse createResponse = new ListingCreateResponse();
        createResponse.setFrom(sellerAddress);
        createResponse.setContent(token);
        createResponse.setDataForSign(Base64Utils.encode(content.getBytes(StandardCharsets.UTF_8)));

        return createResponse;

    }

    /**
     * Verify and create listing data
     * @param request
     */
    public void confirmListing(Web3ExchangeListingConfirmRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        String content = StructContentHash.unwrap(request.getContent());
        ListingDataForSign listingRequest = JsonUtil.fromJson(content, ListingDataForSign.class);
        preListingCheck(
                uid,
                listingRequest.getAssetsId(),
                listingRequest.getQuantity(),
                listingRequest.getCurrency(),
                listingRequest.getPrice(),
                listingRequest.getExpireAt()
        );

        NftListing nftListing = new NftListing();
        nftListing.setUid(uid);
        String uidAddress = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        nftListing.setUidAddress(uidAddress);
        nftListing.setAssetsId(listingRequest.getAssetsId());
        nftListing.setQuantity(listingRequest.getQuantity());
        nftListing.setCurrency(listingRequest.getCurrency());
        nftListing.setPrice(listingRequest.getPrice());
        nftListing.setStartAt(listingRequest.getStartAt());
        nftListing.setExpireAt(listingRequest.getExpireAt());
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
        nftListingService.insert(nftListing);

    }

    /**
     * Create payment transaction
     * @param request
     * @return
     */
    public PaymentCreateResponse createPayment(Web3PaymentCreateRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftListing nftListing = nftListingService.findOne(request.getAssetsId(), request.getListingId());
        preBuyCheck(uid, nftListing);

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
        createPaymentOrder(orderId, expiredAt, uid, nftListing);
        String uidAddress = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        return new PaymentCreateResponse(
                token,
                txnData,
                txnValue,
                txnTo,
                uidAddress
        );
    }

    private void preListingCheck(
            long uid,
            long assetsId,
            int quantity,
            String currency,
            String price,
            long expireAt
    ) {
        if(Times.isExpired(expireAt)){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }
        //Quantity check
        NftBelong nftBelong = nftBelongService.findOne(assetsId, uid);
        if(null == nftBelong || nftBelong.getQuantity() < quantity){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }
        //Check the blockchain is correct
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        String blockchain = walletCurrencyTemplate.findOne(currency).getBlockchain();
        if(!web3Properties.getBlockchain().equals(blockchain)){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }

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

    private String createPaymentToken(String blockchain, WalletOrderBizContent walletOrder) {
        String token = web3WalletProvider.createToken(walletOrder);
        return token;
    }

    private Long createOrderId() {
        return gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
    }

    private void preBuyCheck(Long uid, NftListing nftListing) {
        //Check mint status
        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(nftListing.getAssetsId())
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();
        boolean isMint = assetsDTO.getMint();
        if(!isMint){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_ILLEGAL_MINT_STATUS);
        }
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

    private List<WalletOrderBizContent.Profit> createProfits(NftListing nftListing) {

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
