package com.tenth.nft.exchange.web3.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.NftIdModule;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.templates.*;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.convention.web3.sign.StructContentHash;
import com.tenth.nft.convention.web3.utils.WalletBridgeUrl;
import com.tenth.nft.exchange.common.service.*;
import com.tenth.nft.exchange.web3.dto.ListingCreateResponse;
import com.tenth.nft.exchange.web3.dto.ListingDataForSign;
import com.tenth.nft.exchange.web3.dto.PaymentCreateResponse;
import com.tenth.nft.exchange.web3.vo.*;
import com.tenth.nft.exchange.web3.wallet.Web3WalletProvider;
import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.TpulseContractHelper;
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

import java.nio.charset.StandardCharsets;


/**
 * @author shijie
 */
@Service
public class Web3ListingService extends AbsListingFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Web3ListingService.class);

    @Autowired
    private NftListingService nftListingService;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftActivityService nftExchangeEventService;
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
        String _dataForSign = Base64Utils.encode(JsonUtil.toJson(dataForSign.toDataForSign()).getBytes(StandardCharsets.UTF_8));
        String content = StructContentHash.wrap(JsonUtil.toJson(dataForSign), web3Properties.getRsa().getPrivateKey());

        ListingCreateResponse createResponse = new ListingCreateResponse();
        createResponse.setFrom(sellerAddress);
        createResponse.setContent(content);
        createResponse.setDataForSign(_dataForSign);
        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .sign()
                .put("from", createResponse.getFrom())
                .put("dataForSign", createResponse.getDataForSign())
                .put("content", createResponse.getContent())
                .build();
        createResponse.setWalletBridgeUrl(walletBridgeUrl);

        return createResponse;

    }

    @Override
    protected void preListingCheck(long uid, long assetsId, int quantity, String currency, String price, long expireAt) {
        super.preListingCheck(uid, assetsId, quantity, currency, price, expireAt);
        //Check the blockchain is correct
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        String blockchain = walletCurrencyTemplate.findOne(currency).getBlockchain();
        if(!web3Properties.getBlockchain().equals(blockchain)){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }
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
        PaymentCreateResponse response = new PaymentCreateResponse(
                token,
                txnData,
                txnValue,
                txnTo,
                uidAddress
        );
        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .sign()
                .put("from", response.getFrom())
                .put("txnTo", response.getTxnTo())
                .put("txnValue", response.getTxnValue())
                .put("txnData", response.getTxnData())
                .put("content", response.getContent())
                .build();
        response.setWalletBridgeUrl(walletBridgeUrl);

        return response;
    }

    @Override
    protected void preBuyCheck(Long uid, NftListing nftListing) {
        super.preBuyCheck(uid, nftListing);
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

}
