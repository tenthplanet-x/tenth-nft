package com.tenth.nft.exchange.service;

import com.google.common.base.Strings;
import com.tenth.nft.blockchain.BlockchainContract;
import com.tenth.nft.blockchain.BlockchainGateway;
import com.tenth.nft.blockchain.BlockchainRouter;
import com.tenth.nft.convention.routes.AssetsRebuildRouteRequest;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.convention.wallet.utils.BigNumberUtils;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.NftIdModule;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.blockchain.NullAddress;
import com.tenth.nft.convention.routes.exchange.*;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.operation.BlockchainRouteRequest;
import com.tenth.nft.convention.routes.player.AssetsBelongsUpdateRouteRequest;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.exchange.controller.vo.NftBuyRequest;
import com.tenth.nft.exchange.controller.vo.NftBuyResponse;
import com.tenth.nft.exchange.dto.NftListingDTO;
import com.tenth.nft.exchange.dto.NftOfferDTO;
import com.tenth.nft.exchange.vo.NftSellRequest;
import com.tenth.nft.exchange.vo.SellCancelRequest;
import com.tenth.nft.exchange.wallet.IWalletProvider;
import com.tenth.nft.exchange.wallet.WalletProviderFactory;
import com.tenth.nft.orm.marketplace.dao.*;
import com.tenth.nft.orm.marketplace.dao.expression.*;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.orm.marketplace.entity.event.*;
import com.tenth.nft.protobuf.*;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.SimpleQuerySorts;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class NftExchangeService {

    private Logger LOGGER = LoggerFactory.getLogger(NftExchangeService.class);

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftBelongDao nftBelongDao;
    @Autowired
    private NftMintDao nftMintDao;
    @Autowired
    private NftListingDao nftListingDao;
    @Autowired
    private NftActivityNoCacheDao nftActivityDao;
    @Autowired
    private NftOrderDao nftOrderDao;
    @Autowired
    private NftContractDao nftContractDao;
    @Autowired
    private BlockchainRouter blockChainRouter;
    @Autowired
    private NftOfferDao nftOfferDao;
    @Autowired
    private NftAssetsStatsDao nftAssetsStatsDao;
    @Autowired
    private WalletProviderFactory walletProviderFactory;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;


    public NftListingDTO sell(NftSellRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftExchange.NftListingDTO result = routeClient.send(
                NftExchange.SELL_IC.newBuilder()
                        .setUid(uid)
                        .setAssetsId(request.getAssetsId())
                        .setCurrency(request.getCurrency())
                        .setQuantity(request.getQuantity())
                        .setPrice(request.getPrice())
                        .setStartAt(request.getStartAt())
                        .setExpireAt(request.getExpireAt())
                        .build(),
                SellRouteRequest.class
        ).getListing();

        return NftListingDTO.from(result);

    }

    public NftExchange.NftListingDTO sell(NftExchange.SELL_IC request){

        //quantity check
        NftBelong nftBelong = nftBelongDao.findOne(NftBelongQuery.newBuilder().assetsId(request.getAssetsId()).owner(request.getUid()).build());
        if(null == nftBelong || nftBelong.getQuantity() < request.getQuantity()){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INVALID_PARAMS);
        }
        if(Times.isExpired(request.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_EXCEPTION_INVALID_PARAMS);
        }

        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(nftBelong.getAssetsId())
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();

        NftListing listing = new NftListing();
        listing.setUid(request.getUid());
        listing.setAssetsId(request.getAssetsId());
        listing.setQuantity(request.getQuantity());
        listing.setPrice(request.getPrice());
        listing.setCurrency(request.getCurrency());
        listing.setStartAt(request.getStartAt());
        listing.setExpireAt(request.getExpireAt());
        listing.setCreatedAt(System.currentTimeMillis());
        listing.setUpdatedAt(listing.getCreatedAt());
        listing.setCanceled(false);
        if(!listing.getUid().equals(assetsDTO.getCreator()) && !Strings.isNullOrEmpty(assetsDTO.getCreatorFeeRate())){
            listing.setCreatorUid(assetsDTO.getCreator());
            listing.setCreatorFeeRate(assetsDTO.getCreatorFeeRate());
        }

        //send activity
        Long activityId = sendListingEvent(listing);
        listing.setActivityId(activityId);
        listing = nftListingDao.insert(listing);

        sendListingRouteEvent(listing.getAssetsId());

        return NftListingDTO.toProto(listing);

    }

    public void sellCancel(SellCancelRequest request){
        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        routeClient.send(
                NftExchange.SELL_CANCEL_IC.newBuilder()
                        .setAssetsId(request.getAssetsId())
                        .setListingId(request.getListingId())
                        .setSeller(uid)
                        .setReason(ListCancelEventReason.FORCE.name())
                        .build(),
                SellCancelRouteRequest.class
        );

    }

    public NftExchange.SELL_CANCEL_IS sellCancel(NftExchange.SELL_CANCEL_IC request){
        //listing check
        NftListing nftListing = nftListingDao.findOne(
                NftListingQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getListingId()).build()
        );
        if(null == nftListing){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_CANCEL_EXCEPTION_NOT_EXIST);
        }
        if(Times.isExpired(nftListing.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.SELL_CANCEL_EXCEPTION_EXPIRED);
        }

        nftListingDao.remove(
                NftListingQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getListingId()).build()
        );
        freezeListingEvent(nftListing);
        sendCancelEvent(nftListing, request.getReason());
        sendListingRouteEvent(nftListing.getAssetsId());

        return NftExchange.SELL_CANCEL_IS.newBuilder().build();
    }

    public NftBuyResponse buy(NftBuyRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        NftExchange.BUY_IS payInfo = routeClient.send(
                NftExchange.BUY_IC.newBuilder()
                        .setUid(uid)
                        .setListingId(request.getListingId())
                        .setAssetsId(request.getAssetsId())
                        .build(),
                BuyRouteRequest.class
        );
        NftBuyResponse response = new NftBuyResponse();
        response.setChannel(payInfo.getChannel());
        response.setToken(payInfo.getToken());
        response.setCurrency(payInfo.getCurrency());
        response.setValue(payInfo.getValue());
        return response;

    }

    public NftExchange.BUY_IS buy(NftExchange.BUY_IC request){

        //listing check
        NftListing nftListing = nftListingDao.findOne(
                NftListingQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getListingId()).build()
        );
        if(null == nftListing || nftListing.getCanceled()){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_EXIST);
        }
        if(Times.earlierThan(nftListing.getStartAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NOT_START);
        }
        if(Times.isExpired(nftListing.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_EXPIRED);
        }
        if(nftListing.getUid().equals(request.getUid())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_BELONGS_TO_YOU);
        }

        NftOrder nftOrder = new NftOrder();
        Long orderId = gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
        Long expiredAt = WalletTimes.getExpiredAt();

        //Get pay channel and token
        IWalletProvider walletProvider = walletProviderFactory.getByCurrency(nftListing.getCurrency());
        String channel = walletProvider.getChannel();
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
        String token = walletProvider.createToken(walletOrder);

        nftOrder.setId(orderId);
        nftOrder.setOwner(nftListing.getUid());
        nftOrder.setBuyer(request.getUid());
        nftOrder.setAssetsId(request.getAssetsId());
        nftOrder.setListingId(request.getListingId());
        nftOrder.setCreatedAt(System.currentTimeMillis());
        nftOrder.setUpdatedAt(nftOrder.getCreatedAt());
        nftOrder.setCurrency(nftListing.getCurrency());
        nftOrder.setPrice(nftListing.getPrice());
        nftOrder.setQuantity(nftListing.getQuantity());
        nftOrder.setStatus(NftOrderStatus.CREATE);
        nftOrder.setExpiredAt(expiredAt);
        nftOrderDao.insert(nftOrder);

        return NftExchange.BUY_IS.newBuilder()
                .setChannel(channel)
                .setToken(token)
                .setCurrency(nftListing.getCurrency())
                .setValue(nftListing.getPrice())
                .build();

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

    public NftExchange.PAY_RECEIPT_PUSH_IS payReceiptHandle(NftExchange.PAY_RECEIPT_PUSH_IC request){

        NftExchange.PAY_RECEIPT_PUSH_IS.Builder builder = NftExchange.PAY_RECEIPT_PUSH_IS.newBuilder();

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

    public NftExchange.MINT_IS mint(NftExchange.MINT_IC request){

        try{
            BlockchainContract contract = null;
            BlockchainGateway blockchainGateway = blockChainRouter.get(request.getBlockchain());
            if(Strings.isNullOrEmpty(request.getContractAddress())){
                contract = blockchainGateway.getGlobalNftContract().get();
            }else{
                contract = blockchainGateway.getContract(request.getContractAddress()).get();
            }

            Future<String> tokenFuture = blockchainGateway.mint(contract);
            String token = tokenFuture.get();

            //belongs
            //refreshBelong(request.getOwner(), request.getAssetsId());
            NftBelong belong = new NftBelong();
            belong.setOwner(request.getOwner());
            belong.setAssetsId(request.getAssetsId());
            belong.setQuantity(request.getQuantity());
            belong.setCreatedAt(System.currentTimeMillis());
            belong.setUpdatedAt(belong.getCreatedAt());
            nftBelongDao.insert(belong);
            //Sync to player
            syncToPlayer(belong);

            //Send mint event
            sendMintEvent(request);

            return NftExchange.MINT_IS.newBuilder()
                    .setMint(NftExchange.NftMintDTO.newBuilder()
                            .setBlockchain(request.getBlockchain())
                            .setContractAddress(contract.getAddress())
                            .setTokenStandard(contract.getTokenStandard())
                            .setToken(token)
                            .build())
                    .build();

        }catch (Exception e){
            LOGGER.error("", e);
            throw BizException.newInstance(NftExchangeErrorCodes.MINT_EXCEPTION);
        }

    }

    private void decBelongQuantity(Long owner, Long assetsId, int quantity) {

        NftBelong belong = nftBelongDao.findAndModify(
                NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build(),
                NftBelongUpdate.newBuilder().quantityInc(-quantity).build(),
                UpdateOptions.options().returnNew(true)
        );
        if(belong.getQuantity() <= 0){
            nftBelongDao.remove(NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build());
        }
        //Sync to player
        syncToPlayer(belong);
    }

    private void refrehListing(Long owner, Long assetsId) {

        int rest = 0;
        SimpleQuery preBelongQuery = NftBelongQuery.newBuilder().assetsId(assetsId).owner(owner).build();
        NftBelong preBelong = nftBelongDao.findOne(preBelongQuery);
        if(preBelong != null){
            rest = preBelong.getQuantity();
        }

        //
        final int _rest = rest;
        nftListingDao
                .find(NftListingQuery.newBuilder().assetsId(assetsId).uid(owner).canceled(false).build())
                .stream().forEach(history -> {
                    if(history.getQuantity() > _rest){
                        sellCancel(NftExchange.SELL_CANCEL_IC.newBuilder()
                                .setAssetsId(assetsId)
                                .setListingId(history.getId())
                                .setSeller(owner)
                                .setReason(ListCancelEventReason.QUANTITY.name())
                                .build());
                    }
                });
    }


    public NftExchange.ASSETS_EXCHANGE_PROFILE_IS profile(NftExchange.ASSETS_EXCHANGE_PROFILE_IC request) {

        NftExchange.NftAssetsProfileDTO assetsProfileDTO = profile(request.getAssetsId(), request.getNeedOwners(), request.getObserver());
        return NftExchange.ASSETS_EXCHANGE_PROFILE_IS.newBuilder()
                .setProfile(assetsProfileDTO)
                .build();
    }

    public NftExchange.NftAssetsProfileDTO profile(Long assetsId, boolean needOwnerUids, Long abserver){

        NftExchange.NftAssetsProfileDTO.Builder profileBuilder = NftExchange.NftAssetsProfileDTO.newBuilder();
        profileBuilder.setId(assetsId);

        //get main currency
        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(assetsId)
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();
        String blockchain = assetsDTO.getBlockchain();
        String currency = routeClient.send(
                NftOperation.NFT_BLOCKCHAIN_IC.newBuilder().setBlockchain(blockchain).build(),
                BlockchainRouteRequest.class
        ).getBlockchain().getMainCurrency();

        //current price
        Optional<NftListing> floor = nftListingDao
                .find(NftListingQuery.newBuilder().assetsId(assetsId).build())
                .stream()
                .filter(dto -> !Times.isExpired(dto.getExpireAt()))
                .filter(dto -> dto.getCurrency().equals(currency))
                .sorted(Comparator.comparingLong(NftListing::getCreatedAt))
                .min(Comparator.comparing(listing -> new BigDecimal(listing.getPrice())));
        if(floor.isPresent()){
            profileBuilder.setCurrentListing(NftExchange.NftListingDTO.newBuilder()
                    .setId(floor.get().getId())
                    .setCurrency(floor.get().getCurrency())
                    .setPrice(floor.get().getPrice())
                    .setStartAt(floor.get().getStartAt())
                    .setExpireAt(floor.get().getExpireAt())
                    .setQuantity(floor.get().getQuantity())
                    .setSeller(floor.get().getUid())
                    .setCreatedAt(floor.get().getCreatedAt())
                    .setAssetsId(floor.get().getAssetsId())
                    .build());
        }

        //total volume
        NftAssetsStats stats = nftAssetsStatsDao.findOne(NftAssetsStatsQuery.newBuilder().assetsId(assetsId).build());
        if(null != stats && !Strings.isNullOrEmpty(stats.getTotalVolume()) &&  new BigDecimal(stats.getTotalVolume()).compareTo(BigDecimal.ZERO) > 0){
            profileBuilder.setTotalVolume(stats.getTotalVolume());
            profileBuilder.setCurrency(stats.getCurrency());
        }

        if(!needOwnerUids){
            long owners = nftBelongDao.count(NftBelongQuery.newBuilder().assetsId(assetsId).build());
            profileBuilder.setOwners((int)owners);
            if(owners == 1){
                NftBelong belong = nftBelongDao.findOne(NftBelongQuery.newBuilder().assetsId(assetsId).build());
                profileBuilder.addOwnerLists(belong.getOwner());
            }
        }else{
            profileBuilder.addAllOwnerLists(nftBelongDao
                    .find(NftBelongQuery.newBuilder().assetsId(assetsId).build())
                    .stream()
                    .map(NftBelong::getOwner)
                    .collect(Collectors.toList()));
        }

        if(null != abserver){
            //
            NftBelong abserverBelong = nftBelongDao.findOne(NftBelongQuery.newBuilder().assetsId(assetsId).owner(abserver).build());
            if(null != abserverBelong){
                profileBuilder.setOwns(abserverBelong.getQuantity());
            }
        }

        //bestOffer
        Optional<NftOffer> nftOffer = nftOfferDao.find(NftOfferQuery.newBuilder()
                .assetsId(assetsId)
                .setSorts(SimpleQuerySorts.newBuilder().sort("price", true).sort("createdAt", true).build())
                .build()
        ).stream().filter(dto -> !Times.isExpired(dto.getExpireAt())).findFirst();
        if(nftOffer.isPresent()){
            profileBuilder.setBestOffer(NftOfferDTO.from(nftOffer.get()));
        }

        return profileBuilder.build();
    }

    public NftExchange.COLLECTION_EXCHANGE_PROFILE_IS profile(NftExchange.COLLECTION_EXCHANGE_PROFILE_IC request){

        NftExchange.NftCollectionProfileDTO.Builder collectionProfileDTOBuilder = NftExchange.NftCollectionProfileDTO.newBuilder();
        Set<Long> ownerLists = new HashSet<>();

        request.getAssetsIdsList().stream().forEach(assetsId -> {

            NftAssetsStats stats = nftAssetsStatsDao.findOne(NftAssetsStatsQuery.newBuilder().assetsId(assetsId).build());
            if(null != stats){
                if(!Strings.isNullOrEmpty(stats.getTotalVolume()) && new BigDecimal(stats.getTotalVolume()).compareTo(BigDecimal.ZERO) > 0){
                    collectionProfileDTOBuilder.setTotalVolume(collectionProfileDTOBuilder.getTotalVolume() + stats.getTotalVolume());
                    collectionProfileDTOBuilder.setCurrency(stats.getCurrency());
                }
                if(!Strings.isNullOrEmpty(stats.getFloorPrice()) && new BigDecimal(stats.getFloorPrice()).compareTo(BigDecimal.ZERO) > 0){
                    collectionProfileDTOBuilder.setFloorPrice(collectionProfileDTOBuilder.getFloorPrice() + stats.getFloorPrice());
                    collectionProfileDTOBuilder.setCurrency(stats.getCurrency());
                }
            }

            //ownerUids
            ownerLists.addAll(nftBelongDao.find(NftBelongQuery.newBuilder().assetsId(assetsId).build())
                    .stream()
                    .map(belong -> belong.getOwner())
                    .collect(Collectors.toList()));

        });

        collectionProfileDTOBuilder.setOwners(ownerLists.size());
        if(request.hasObserver()){
            collectionProfileDTOBuilder.setOwned(ownerLists.contains(request.getObserver()));
        }

        return NftExchange.COLLECTION_EXCHANGE_PROFILE_IS.newBuilder().setProfile(collectionProfileDTOBuilder.build()).build();

    }



    private void sendMintEvent(NftExchange.MINT_IC request) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(request.getAssetsId());
        activity.setType(NftActivityEventType.Minted);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        MintEvent mintEvent = new MintEvent();
        mintEvent.setFrom(NullAddress.TOKEN);
        mintEvent.setTo(request.getOwner());
        mintEvent.setQuantity(request.getQuantity());
        activity.setMint(mintEvent);;

        nftActivityDao.insert(activity);
    }

    private Long sendListingEvent(NftListing listing) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(listing.getAssetsId());
        activity.setType(NftActivityEventType.List);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        ListEvent listEvent = new ListEvent();
        listEvent.setFrom(listing.getUid());
        listEvent.setPrice(listing.getPrice());
        listEvent.setQuantity(listing.getQuantity());
        listEvent.setCurrency(listing.getCurrency());
        listEvent.setExpireAt(listing.getExpireAt());
        activity.setList(listEvent);

        return nftActivityDao.insert(activity).getId();

    }

    private void freezeListingEvent(NftListing nftListing) {

        if(null != nftListing.getActivityId()){
            nftActivityDao.update(
                    NftActivityQuery.newBuilder().id(nftListing.getActivityId()).build(),
                    NftActivityUpdate.newBuilder().freeze(true).build()
            );
        }

    }


    private void sendTransferEvent(NftOrder nftOrder) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(nftOrder.getAssetsId());
        activity.setType(NftActivityEventType.Transfer);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        TransferEvent transfer = new TransferEvent();
        transfer.setFrom(nftOrder.getOwner());
        transfer.setTo(nftOrder.getBuyer());
        transfer.setQuantity(nftOrder.getQuantity());
        transfer.setPrice(nftOrder.getPrice());
        transfer.setCurrency(nftOrder.getCurrency());

        activity.setTransfer(transfer);

        nftActivityDao.insert(activity);

    }

    private void sendSaleEvent(NftOrder nftOrder) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(nftOrder.getAssetsId());
        activity.setType(NftActivityEventType.Sale);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        SaleEvent sale = new SaleEvent();
        sale.setFrom(nftOrder.getOwner());
        sale.setTo(nftOrder.getBuyer());
        sale.setQuantity(nftOrder.getQuantity());
        sale.setPrice(nftOrder.getPrice());
        sale.setCurrency(nftOrder.getCurrency());
        activity.setSale(sale);

        nftActivityDao.insert(activity);

    }

    private void sendCancelEvent(NftListing nftListing, String reason) {

        NftActivity activity = new NftActivity();
        activity.setAssetsId(nftListing.getAssetsId());
        activity.setType(NftActivityEventType.Cancel);
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setUpdatedAt(activity.getCreatedAt());

        ListCancelEvent list = new ListCancelEvent();
        list.setFrom(nftListing.getUid());
        list.setQuantity(nftListing.getQuantity());
        list.setPrice(nftListing.getPrice());
        list.setCurrency(nftListing.getCurrency());
        list.setReason(reason);
        activity.setCancel(list);

        nftActivityDao.insert(activity);
    }

    public void listingBuyConfirm(NftOrder nftOrder){

        try{
            _buyConfirm(nftOrder);
        }finally {
            NftListing nftListing = nftListingDao.findOne(
                    NftListingQuery.newBuilder().assetsId(nftOrder.getAssetsId()).id(nftOrder.getListingId()).build()
            );
            freezeListingEvent(nftListing);
            //
            nftListingDao.remove(
                    NftListingQuery.newBuilder().assetsId(nftListing.getAssetsId()).id(nftListing.getId()).build()
            );
            //owner listing refresh
            refrehListing(nftOrder.getOwner(), nftOrder.getAssetsId());
            sendListingRouteEvent(nftOrder.getAssetsId());

            //rebuild assets
            routeClient.send(
                    NftSearch.NFT_ASSETS_REBUILD_IC.newBuilder()
                            .setAssetsId(nftOrder.getAssetsId())
                            .build(),
                    AssetsRebuildRouteRequest.class
            );
        }

    }


    public void _buyConfirm(NftOrder nftOrder) {

        //listing check
        NftListing nftListing = nftListingDao.findOne(
                NftListingQuery.newBuilder().assetsId(nftOrder.getAssetsId()).id(nftOrder.getListingId()).build()
        );
        if(null == nftListing || nftListing.getCanceled()){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_EXIST);
        }
        if(Times.earlierThan(nftListing.getStartAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NOT_START);
        }
        if(Times.isExpired(nftListing.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_EXPIRED);
        }

        NftBelong nftBelong = nftBelongDao.findOne(NftBelongQuery.newBuilder().assetsId(nftOrder.getAssetsId()).owner(nftOrder.getOwner()).build());
        if(null == nftBelong || nftBelong.getQuantity() < nftOrder.getQuantity()){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_ENOUGH_QUANTITY);
        }

        nftOrder.setStatus(NftOrderStatus.COMPLETE);
        nftOrderDao.update(
                NftOrderQuery.newBuilder().assetsId(nftOrder.getAssetsId()).id(nftOrder.getId()).build(),
                NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.COMPLETE).build()
        );


        //change belongs
        nftBelong = nftBelongDao.findAndModify(
                NftBelongQuery.newBuilder().assetsId(nftOrder.getAssetsId()).owner(nftOrder.getBuyer()).build(),
                NftBelongUpdate.newBuilder()
                        .quantityInc(nftOrder.getQuantity())
                        .createdAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true).returnNew(true)
        );
        //sync to player
        syncToPlayer(nftBelong);

        //send events
        sendTransferEvent(nftOrder);
        sendSaleEvent(nftOrder);

        //refresh owner belongs
        decBelongQuantity(nftOrder.getOwner(), nftOrder.getAssetsId(), nftOrder.getQuantity());

        sendExchangeRouteEvent(nftOrder.getAssetsId());

    }


    public void expireCheck(NftExchange.LISTING_EXPIRE_CHECK_IC request) {
        nftListingDao.remove(NftListingQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getListingId()).build());
        sendListingRouteEvent(request.getAssetsId());
    }

    private void sendListingRouteEvent(Long assetsId) {
        routeClient.send(
                NftExchange.LISTING_EVENT_IC.newBuilder().setAssetsId(assetsId).build(),
                ListingEventRouteRequest.class
        );
    }

    private void sendExchangeRouteEvent(Long assetsId) {
        routeClient.send(
                NftExchange.EXCHANGE_EVENT_IC.newBuilder().setAssetsId(assetsId).build(),
                ExchangeEventRouteRequest.class
        );
    }

    private void syncToPlayer(NftBelong belong) {
        routeClient.send(
                NftPlayer.ASSETS_BELONGS_UPDATE_IC.newBuilder()
                        .setUid(belong.getOwner())
                        .setAssetsId(belong.getAssetsId())
                        .setOwns(belong.getQuantity())
                        .build(),
                AssetsBelongsUpdateRouteRequest.class
        );
    }
}
