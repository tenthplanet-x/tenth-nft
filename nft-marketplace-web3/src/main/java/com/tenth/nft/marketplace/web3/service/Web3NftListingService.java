package com.tenth.nft.marketplace.web3.service;

import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.web3wallet.Web3WalletBalanceRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.web3.sign.ListingDataForSign;
import com.tenth.nft.convention.web3.sign.StructContentHash;
import com.tenth.nft.convention.web3.utils.WalletBridgeUrl;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;
import com.tenth.nft.marketplace.common.dto.NftWalletPayTicket;
import com.tenth.nft.marketplace.common.entity.AbsNftOrder;
import com.tenth.nft.marketplace.common.service.AbsNftListingService;
import com.tenth.nft.marketplace.common.vo.NftListingBuyRequest;
import com.tenth.nft.marketplace.common.vo.NftListingCancelRequest;
import com.tenth.nft.marketplace.common.vo.NftListingCreateRequest;
import com.tenth.nft.marketplace.common.vo.NftListingListRequest;
import com.tenth.nft.marketplace.web3.dao.Web3NftListingDao;
import com.tenth.nft.marketplace.web3.dto.Web3SendTransactionTicket;
import com.tenth.nft.marketplace.web3.dto.WebListingSignTicket;
import com.tenth.nft.marketplace.web3.entity.Web3NftAssets;
import com.tenth.nft.marketplace.web3.entity.Web3NftListing;
import com.tenth.nft.marketplace.web3.vo.Web3NftAssetsCreateConfirmRequest;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftWeb3Exchange;
import com.tenth.nft.protobuf.NftWeb3Wallet;
import com.tenth.nft.solidity.TpulseContractHelper;
import com.tpulse.gs.convention.cypher.utils.Base64Utils;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.utils.Convert;

import java.nio.charset.StandardCharsets;

/**
 * @author shijie
 */
@Service
public class Web3NftListingService extends AbsNftListingService<Web3NftListing> {

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private TpulseContractHelper tpulseContractHelper;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    private Web3NftAssetsService nftAssetsService;
    private Web3NftListingDao nftListingDao;
    private Web3NftBuyOrderService web3NftBuyOrderService;

    public Web3NftListingService(
            Web3NftAssetsService nftAssetsService,
            Web3NftListingDao nftListingDao,
            Web3NftBelongService nftBelongService,
            Web3NftUbtLogService nftUbtLogService,
            Web3NftBuyOrderService web3NftBuyOrderService

    ) {
        super(nftAssetsService, nftListingDao, nftBelongService, nftUbtLogService, web3NftBuyOrderService);
        this.nftAssetsService = nftAssetsService;
        this.nftListingDao = nftListingDao;
        this.web3NftBuyOrderService = web3NftBuyOrderService;
    }

    @Override
    protected Web3NftListing newListingEntity() {
        return new Web3NftListing();
    }

    public WebListingSignTicket create(NftListingCreateRequest request) throws Exception {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        //Get user web3 address
        String seller = getUidAddress(uid);

        Web3NftAssets nftAssets = nftAssetsService.findOne(request.getAssetsId());
        preListingCheck(
                seller,
                request,
                nftAssets
        );

        ListingDataForSign dataForSign = new ListingDataForSign();

        dataForSign.setSeller(seller);
        dataForSign.setDomain(tpulseContractHelper.getDomain());
        dataForSign.setAssetsId(request.getAssetsId());
        dataForSign.setQuantity(request.getQuantity());
        dataForSign.setPrice(request.getPrice());
        dataForSign.setCurrency(request.getCurrency());
        dataForSign.setStartAt(request.getStartAt());
        dataForSign.setExpireAt(request.getExpireAt());
        String _dataForSign = Base64Utils.encode(JsonUtil.toJson(dataForSign.toDataForSign()).getBytes(StandardCharsets.UTF_8));
        String content = StructContentHash.wrap(JsonUtil.toJson(request), web3Properties.getRsa().getPrivateKey());

        WebListingSignTicket createResponse = new WebListingSignTicket();
        createResponse.setFrom(seller);
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

    public NftListingDTO createConfirm(Web3NftAssetsCreateConfirmRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String seller = getUidAddress(uid);
        //Get user web3 address
        String content = StructContentHash.unwrap(request.getContent());
        NftListingCreateRequest listingRequest = JsonUtil.fromJson(content, NftListingCreateRequest.class);
        return create(seller, listingRequest);
    }

    public void cancel(NftListingCancelRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String seller = getUidAddress(uid);
        cancel(seller, request);

    }

    public Page<NftListingDTO> list(NftListingListRequest request) {
        return super.list(request, NftListingDTO.class);
    }

    public Web3SendTransactionTicket buy(NftListingBuyRequest request) {
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        String buyer = getUidAddress(uid);
        NftWalletPayTicket ticket = super.buy(buyer, request);

        Web3NftListing nftListing = findOne(request.getAssetsId(), request.getListingId());
        //create transaction params
        //get walletAddress
        String txnData = tpulseContractHelper.createPaymentTransactionData(
                nftListing.getSeller(),
                nftListing.getAssetsId(),
                nftListing.getQuantity(),
                nftListing.getPrice(),
                nftListing.getSignature()
        );
        String txnValue = Convert.toWei(nftListing.getPrice(), Convert.Unit.ETHER).toBigInteger().toString();
        String txnTo = tpulseContractHelper.getContractAddress();
        Web3SendTransactionTicket response = new Web3SendTransactionTicket(
                ticket.getContent(),
                txnData,
                txnValue,
                txnTo,
                buyer
        );
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

        AbsNftOrder nftOrder = web3NftBuyOrderService.findOne(
                request.getAssetsId(),
                Long.valueOf(request.getOrderId())
        );
        web3NftBuyOrderService.updateTxn(
                nftOrder.getAssetsId(),
                nftOrder.getId(),
                request.getTxn()
        );

        return res;
    }
}
