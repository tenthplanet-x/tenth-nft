package com.tenth.nft.marketplace.web3.service;


import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.wallet.WalletMerchantType;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.WalletOrderType;
import com.tenth.nft.convention.wallet.WalletProductCode;
import com.tenth.nft.convention.web3.sign.OfferDataForSign;
import com.tenth.nft.convention.web3.sign.StructContentHash;
import com.tenth.nft.convention.web3.utils.WalletBridgeUrl;
import com.tenth.nft.marketplace.common.dto.NftOfferDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tenth.nft.marketplace.common.entity.AbsNftOrder;
import com.tenth.nft.marketplace.common.service.AbsNftOfferService;
import com.tenth.nft.marketplace.common.vo.NftMakeOfferRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferAcceptRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferCancelRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferListRequest;
import com.tenth.nft.marketplace.common.wallet.IWalletProvider;
import com.tenth.nft.marketplace.common.wallet.WalletProviderFactory;
import com.tenth.nft.marketplace.web3.dao.Web3NftOfferDao;
import com.tenth.nft.marketplace.web3.dto.Web3AcceptCreateSignTicket;
import com.tenth.nft.marketplace.web3.dto.Web3OfferCreateSignTicket;
import com.tenth.nft.marketplace.web3.entity.Web3NftAssets;
import com.tenth.nft.marketplace.web3.entity.Web3NftOffer;
import com.tenth.nft.marketplace.web3.vo.Web3OfferConfirmRequest;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Wallet;

import java.math.BigDecimal;

/**
 * @author shijie
 */
@Service
public class Web3NftOfferService extends AbsNftOfferService<Web3NftOffer> {

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private WalletProviderFactory walletProviderFactory;

    private Web3NftAssetsService nftAssetsService;

    private Web3NftAcceptOrderService nftAcceptOrderService;

    public Web3NftOfferService(
            Web3NftOfferDao nftOfferDao,
            Web3NftAssetsService nftAssetsService,
            Web3NftBelongService nftBelongService,
            Web3NftUbtLogService nftUbtLogService,
            Web3NftAcceptOrderService nftAcceptOrderService,
            RouteClient routeClient) {
        super(nftOfferDao, nftAssetsService, nftBelongService, nftUbtLogService, nftAcceptOrderService, routeClient);
        this.nftAssetsService = nftAssetsService;
        this.nftAcceptOrderService = nftAcceptOrderService;
    }

    @Override
    protected Web3NftOffer newNftOffer() {
        return new Web3NftOffer();
    }

    public Page<NftOfferDTO> list(NftOfferListRequest request) {

        Page<NftOfferDTO> dataPage = list(request, NftOfferDTO.class);

//        if(!dataPage.getData().isEmpty()){
//            Collection<Long> sellerUids = dataPage.getData().stream().map(dto -> Long.valueOf(dto.getBuyer())).collect(Collectors.toSet());
//            Map<Long, NftUserProfileDTO> userProfileDTOMap = routeClient.send(
//                    Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(sellerUids).build(),
//                    SearchUserProfileRouteRequest.class
//            ).getProfilesList().stream().map(NftUserProfileDTO::from).collect(Collectors.toMap(NftUserProfileDTO::getUid, Function.identity()));
//            dataPage.getData().stream().forEach(dto -> {
//                dto.setUserProfile(userProfileDTOMap.get(dto.getBuyer()));
//            });
//        }

        return dataPage;

    }

    public Web3OfferCreateSignTicket makeOffer(NftMakeOfferRequest request) throws Exception {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        Web3NftAssets nftAssets = nftAssetsService.findById(request.getAssetsId());
        preMakeOfferCheck(
                String.valueOf(uid),
               request,
                nftAssets
        );

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
        String content = StructContentHash.wrap(JsonUtil.toJson(request), web3Properties.getRsa().getPrivateKey());

        Web3OfferCreateSignTicket response = new Web3OfferCreateSignTicket(from, dataForSign, content);
        String walletBridgeUrl = WalletBridgeUrl.newBuilder(web3Properties)
                .sign()
                .put("from", response.getFrom())
                .put("dataForSign", response.getDataForSign())
                .put("content", response.getContent())
                .build();
        response.setWalletBridgeUrl(walletBridgeUrl);

        return response;
    }


    public NftOfferDTO makeOfferConfirm(Web3OfferConfirmRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        String content = request.getContent();
        NftMakeOfferRequest makeOfferRequest = JsonUtil.fromJson(StructContentHash.unwrap(content), NftMakeOfferRequest.class);
        makeOfferRequest.setSignature(request.getSignature());

        NftOfferDTO dto = (NftOfferDTO)makeOffer(String.valueOf(uid), makeOfferRequest);
//        dto.setUserProfile(
//                NftUserProfileDTO.from(
//                        routeClient.send(
//                                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(Long.valueOf(dto.getBuyer())).build(),
//                                SearchUserProfileRouteRequest.class
//                        ).getProfiles(0)
//                )
//        );
        return dto;
    }

    @Override
    protected NftOfferDTO newDTO() {
        return new NftOfferDTO();
    }

    public void cancel(NftOfferCancelRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        cancel(String.valueOf(uid), request);
    }

    public Web3AcceptCreateSignTicket accept(NftOfferAcceptRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String seller = getUidAddress(uid);
        AbsNftOrder order = accept(seller, request);

        //create transaction params
        Web3NftOffer nftOffer = findOne(request.getAssetsId(), request.getOfferId());
        Web3NftAssets nftAssets = nftAssetsService.findById(request.getAssetsId());
        //get walletAddress
        String txnData = tpulseContractHelper.createAcceptTransactionData(
                seller,
                nftOffer.getAssetsId(),
                nftOffer.getQuantity(),
                nftOffer.getPrice(),
                nftOffer.getExpireAt(),
                null
        );
        String txnValue = BigDecimal.ZERO.toString();
        String txnTo = tpulseContractHelper.getContractAddress();
        //Get pay channel and token
        WalletOrderBizContent walletOrder = WalletOrderBizContent.newBuilder()
                .activityCfgId(WalletOrderType.NftExpense.getActivityCfgId())
                .productCode(WalletProductCode.NFT_OFFER.name())
                .productId(String.valueOf(nftOffer.getAssetsId()))
                .outOrderId(String.valueOf(order.getId()))
                .merchantType(WalletMerchantType.PERSONAL.name())
                .merchantId(seller)
                .currency(nftOffer.getCurrency())
                .value(nftOffer.getPrice())
                .expiredAt(order.getExpiredAt())
                .remark("")
                .profits(createProfits(seller, nftOffer))
                .build();

        IWalletProvider walletProvider = walletProviderFactory.get(nftAssets.getBlockchain());
        String content = walletProvider.createToken(walletOrder);

        Web3AcceptCreateSignTicket response = new Web3AcceptCreateSignTicket();
        response.setContent(content);
        response.setFrom(seller);
        response.setTxnTo(txnTo);
        response.setTxnValue(txnValue);
        response.setTxnData(txnData);
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

    protected String getUidAddress(Long uid) {
        String seller = routeClient.send(
                NftWeb3Wallet.WEB3_WALLET_BALANCE_IC.newBuilder()
                        .setUid(uid)
                        .setNeedBalance(false)
                        .build(),
                Web3WalletBalanceRouteRequest.class
        ).getBalance().getAddress();
        return seller;
    }

    public NftExchange.PAYMENT_RECEIVE_IS receivePayment(NftWeb3Exchange.WEB3_PAYMENT_CONFIRM_IC request) {

        NftExchange.PAYMENT_RECEIVE_IC receive = NftExchange.PAYMENT_RECEIVE_IC.newBuilder()
                .setAssetsId(request.getAssetsId())
                .setOrderId(request.getOrderId())
                .setState(request.getState())
                .build();

        NftExchange.PAYMENT_RECEIVE_IS res = receiveReceipt(receive);

        AbsNftOrder nftOrder = nftAcceptOrderService.findOne(
                request.getAssetsId(),
                Long.valueOf(request.getOrderId())
        );
        nftAcceptOrderService.updateTxn(
                nftOrder.getAssetsId(),
                nftOrder.getId(),
                request.getTxn()
        );

        return res;
    }
}
