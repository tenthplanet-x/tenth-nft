package com.tenth.nft.exchange.buildin.service;

import com.google.common.base.Strings;
import com.tenth.nft.blockchain.BlockchainRouter;
import com.tenth.nft.convention.*;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.convention.blockchain.NullAddress;
import com.tenth.nft.convention.routes.exchange.*;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.convention.web3.utils.HexAddresses;
import com.tenth.nft.exchange.buildin.controller.vo.NftBuyRequest;
import com.tenth.nft.exchange.buildin.controller.vo.NftBuyResponse;
import com.tenth.nft.exchange.buildin.dto.NftListingDTO;
import com.tenth.nft.exchange.buildin.dto.NftOfferDTO;
import com.tenth.nft.exchange.buildin.vo.NftSellRequest;
import com.tenth.nft.exchange.buildin.wallet.BuildInWalletProvider;
import com.tenth.nft.exchange.common.service.AbsListingFlowService;
import com.tenth.nft.exchange.common.service.NftActivityService;
import com.tenth.nft.exchange.common.service.NftBelongService;
import com.tenth.nft.exchange.common.service.NftListingService;
import com.tenth.nft.orm.marketplace.dao.*;
import com.tenth.nft.orm.marketplace.dao.expression.*;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.orm.marketplace.entity.event.*;
import com.tenth.nft.protobuf.*;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.SimpleQuerySorts;
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
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class BuildInListingService extends AbsListingFlowService {

    private Logger LOGGER = LoggerFactory.getLogger(BuildInListingService.class);

    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftOrderDao nftOrderDao;
    @Autowired
    private BlockchainRouter blockChainRouter;
    @Autowired
    private NftOfferDao nftOfferDao;
    @Autowired
    private NftAssetsStatsDao nftAssetsStatsDao;
    @Autowired
    private BuildInWalletProvider buildInWalletProvider;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;
    @Autowired
    private NftListingService nftListingService;
    @Autowired
    private NftBelongService nftBelongService;
    @Autowired
    private BuildInProperties buildInProperties;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private NftActivityService nftActivityService;

    public NftListingDTO create(NftSellRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        preListingCheck(
            uid,
            request.getAssetsId(),
            request.getQuantity(),
                request.getCurrency(),
                request.getPrice(),
                request.getExpireAt()
        );

        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(request.getAssetsId())
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();
        NftListing listing = new NftListing();
        listing.setUid(uid);
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
        nftListingService.insert(listing);

        NftListingDTO dto = new NftListingDTO();
        dto.setId(listing.getId());
        dto.setAssetsId(listing.getAssetsId());
        dto.setCurrency(listing.getCurrency());
        dto.setPrice(listing.getPrice());
        dto.setQuantity(listing.getQuantity());
        dto.setSeller(uid);
        dto.setStartAt(listing.getStartAt());
        dto.setExpireAt(listing.getExpireAt());
        dto.setCreatedAt(listing.getCreatedAt());
        return dto;

    }

    public NftBuyResponse buy(NftBuyRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        //listing check
        NftListing nftListing = nftListingService.findOne(request.getAssetsId(), request.getListingId());
        preBuyCheck(uid, nftListing);

        NftOrder nftOrder = new NftOrder();
        Long orderId = gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
        Long expiredAt = WalletTimes.getExpiredAt();

        //Get pay channel and token
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
        String token = buildInWalletProvider.createToken(walletOrder);

        nftOrder.setId(orderId);
        nftOrder.setOwner(nftListing.getUid());
        nftOrder.setBuyer(uid);
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

        NftBuyResponse response = new NftBuyResponse();
        response.setChannel(WalletPayChannel.BUILDIN.name());
        response.setContent(token);
        response.setCurrency(walletOrder.getCurrency());
        response.setValue(walletOrder.getValue());
        return response;

    }


    public NftExchange.PAYMENT_RECEIVE_IS receivePayment(NftExchange.PAYMENT_RECEIVE_IC request){

        NftExchange.PAYMENT_RECEIVE_IS.Builder builder = NftExchange.PAYMENT_RECEIVE_IS.newBuilder();

        Long orderId = Long.valueOf(request.getOrderId());
        SimpleQuery orderQuery = NftOrderQuery.newBuilder().assetsId(request.getAssetsId()).id(orderId).build();
        NftOrder nftOrder = nftOrderDao.findOne(orderQuery);
        if(null != nftOrder && NftOrderStatus.CREATE.equals(nftOrder.getStatus())){

            //Order of listing process
            if(null != nftOrder.getListingId()){
                WalletBillState state = WalletBillState.valueOf(request.getState());
                switch (state){
                    case PAYED:
                        try {
                            listingPaymentConfirm(nftOrder);
                            builder.setRefund(false);
                        }catch (BizException e){
                            LOGGER.error("", e);
                            builder.setRefund(true);
                        }
                        break;
                    case FAIL:
                        nftOrderDao.update(
                                orderQuery,
                                NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.CANCEL).remark("pay failed").build()
                        );
                        builder.setRefund(false);
                        break;
                }
            }
            //TODO Unknown stage
        }

        return builder.build();

    }

    public NftExchange.ASSETS_EXCHANGE_PROFILE_IS profile(NftExchange.ASSETS_EXCHANGE_PROFILE_IC request) {
        NftExchange.NftAssetsProfileDTO assetsProfileDTO = profile(request.getAssetsId(), request.getNeedOwners(), request.getObserver());
        return NftExchange.ASSETS_EXCHANGE_PROFILE_IS.newBuilder()
                .setProfile(assetsProfileDTO)
                .build();
    }

    /**
     * @param assetsId
     * @param needOwnerUids Return information of uids they have assets when this is true
     * @param observer Return assets belong to observer when this is true
     * @return
     */
    public NftExchange.NftAssetsProfileDTO profile(Long assetsId, boolean needOwnerUids, Long observer){

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
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        String currency = walletCurrencyTemplate.findMainCurrency(blockchain).getCode();

        if(null != observer){
            NftBelong observerBelong = nftBelongService.findOne(assetsId, observer);//nftBelongDao.findOne(NftBelongQuery.newBuilder().assetsId(assetsId).owner(observer).build());
            if(null != observerBelong){
                profileBuilder.setOwns(observerBelong.getQuantity());
            }
        }

        if(!needOwnerUids){
            long owners = nftBelongService.ownersOf(assetsId);//nftBelongDao.count(NftBelongQuery.newBuilder().assetsId(assetsId).build());
            profileBuilder.setOwners((int)owners);
            if(owners == 1){
                NftBelong belong = nftBelongService.findAll(assetsId).get(0);//nftBelongDao.findOne(NftBelongQuery.newBuilder().assetsId(assetsId).build());
                profileBuilder.addOwnerLists(belong.getOwner());
            }
        }else{
            profileBuilder.addAllOwnerLists(nftBelongService
                    .findAll(assetsId)
                    .stream()
                    .map(NftBelong::getOwner)
                    .collect(Collectors.toList()));
        }

        //total volume
        NftAssetsStats stats = nftAssetsStatsDao.findOne(NftAssetsStatsQuery.newBuilder().assetsId(assetsId).build());
        if(null != stats && !Strings.isNullOrEmpty(stats.getTotalVolume()) &&  new BigDecimal(stats.getTotalVolume()).compareTo(BigDecimal.ZERO) > 0){
            profileBuilder.setTotalVolume(stats.getTotalVolume());
            profileBuilder.setCurrency(stats.getCurrency());
        }
        //floor price
        Optional<NftListing> floorListing = nftListingService.findFloorListing(assetsId, currency);
        if(floorListing.isPresent()){
            profileBuilder.setCurrentListing(NftExchange.NftListingDTO.newBuilder()
                    .setId(floorListing.get().getId())
                    .setCurrency(floorListing.get().getCurrency())
                    .setPrice(floorListing.get().getPrice())
                    .setStartAt(floorListing.get().getStartAt())
                    .setExpireAt(floorListing.get().getExpireAt())
                    .setQuantity(floorListing.get().getQuantity())
                    .setSeller(floorListing.get().getUid())
                    .setCreatedAt(floorListing.get().getCreatedAt())
                    .setAssetsId(floorListing.get().getAssetsId())
                    .build());
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
            ownerLists.addAll(nftBelongService.findAll(assetsId)
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

    public void listingPaymentConfirm(NftOrder nftOrder){
        try{
            _listingPaymentConfirm(nftOrder);
        }finally {
            NftListing nftListing = nftListingService.findOne(nftOrder.getAssetsId(), nftOrder.getListingId());
            nftListingService.remove(nftListing.getAssetsId(), nftListing.getId());
            //Refresh all listings belong to owner
            nftListingService.refreshListingsBelongTo(nftOrder.getOwner(), nftOrder.getAssetsId());
        }
    }

    public void _listingPaymentConfirm(NftOrder nftOrder) {

        NftBelong nftBelong = nftBelongService.findOne(nftOrder.getAssetsId(), nftOrder.getOwner());
        if(null == nftBelong || nftBelong.getQuantity() < nftOrder.getQuantity()){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_ENOUGH_QUANTITY);
        }

        nftOrder.setStatus(NftOrderStatus.COMPLETE);
        nftOrderDao.update(
                NftOrderQuery.newBuilder().assetsId(nftOrder.getAssetsId()).id(nftOrder.getId()).build(),
                NftOrderUpdate.newBuilder().setStatus(NftOrderStatus.COMPLETE).build()
        );

        //Change(inc) the quantity of assets belongs to buyer
        nftBelongService.inc(nftOrder.getAssetsId(), nftOrder.getBuyer(), nftOrder.getQuantity());
        //Change(dec) the quantity of assets belongs to buyer
        nftBelongService.dec(nftOrder.getAssetsId(), nftOrder.getOwner(), nftOrder.getQuantity());
        //Create events
        nftActivityService.sendTransferEvent(nftOrder);
        nftActivityService.sendSaleEvent(nftOrder);
        //Send events to stats
        sendExchangeRouteEventToStats(nftOrder.getAssetsId());
    }

    public void expireCheck(NftExchange.LISTING_EXPIRE_CHECK_IC request) {
        nftListingService.cancel(request.getAssetsId(), request.getListingId(), ListCancelEventReason.EXPIRED.name());
    }

    public void sendExchangeRouteEventToStats(Long assetsId) {
        routeClient.send(
                NftExchange.EXCHANGE_EVENT_IC.newBuilder().setAssetsId(assetsId).build(),
                ExchangeEventRouteRequest.class
        );
    }

}
