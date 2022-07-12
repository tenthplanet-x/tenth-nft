package com.tenth.nft.exchange.service;

import com.google.common.base.Strings;
import com.tenth.nft.blockchain.BlockchainContract;
import com.tenth.nft.blockchain.BlockchainGateway;
import com.tenth.nft.blockchain.BlockchainRouter;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.blockchain.NullAddress;
import com.tenth.nft.convention.routes.exchange.BuyRouteRequest;
import com.tenth.nft.convention.routes.exchange.SellRouteRequest;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.exchange.controller.vo.NftBuyRequest;
import com.tenth.nft.exchange.dto.NftListingDTO;
import com.tenth.nft.exchange.vo.NftSellRequest;
import com.tenth.nft.orm.marketplace.dao.*;
import com.tenth.nft.orm.marketplace.dao.expression.*;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.orm.marketplace.entity.event.ListEvent;
import com.tenth.nft.orm.marketplace.entity.event.MintEvent;
import com.tenth.nft.orm.marketplace.entity.event.SaleEvent;
import com.tenth.nft.orm.marketplace.entity.event.TransferEvent;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.convention.dao.SimpleQuery;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private NftActivityDao nftActivityDao;
    @Autowired
    private NftOrderDao nftOrderDao;
    @Autowired
    private NftContractDao nftContractDao;
    @Autowired
    private BlockchainRouter blockChainRouter;

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
            throw BizException.newInstance(NftExchangeErrorCodes.EXCHANGE_EXCEPTION_NO_ENOUGH_QUANTITY);
        }

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
        listing = nftListingDao.insert(listing);

        sendListingEvent(listing);

        return NftListingDTO.toProto(listing);

    }

    public void buy(NftBuyRequest request) {

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        routeClient.send(
                NftExchange.BUY_IC.newBuilder()
                        .setUid(uid)
                        .setListingId(request.getListingId())
                        .setAssetsId(request.getAssetsId())
                        .build(),
                BuyRouteRequest.class
        );
    }

    public void buy(NftExchange.BUY_IC request){

        //listing check
        NftListing nftListing = nftListingDao.findOne(
                NftListingQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getListingId()).build()
        );
        if(Times.earlierThan(nftListing.getStartAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NOT_START);
        }
        if(Times.isExpired(nftListing.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_EXPIRED);
        }
        if(nftListing.getCanceled()){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_CANCELED);
        }
        NftBelong nftBelong = nftBelongDao.findOne(NftBelongQuery.newBuilder().assetsId(nftListing.getAssetsId()).owner(nftListing.getUid()).build());
        if(null == nftBelong || nftBelong.getQuantity() < nftListing.getQuantity()){
            //todo cancel reason
            nftListingDao.update(
                    NftListingQuery.newBuilder().assetsId(nftListing.getAssetsId()).id(nftListing.getId()).build(),
                    NftListingUpdate.newBuilder().canceled(true).build()
            );
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_ENOUGH_QUANTITY);
        }

        //TODO wallet check

        NftOrder nftOrder = new NftOrder();
        nftOrder.setOwner(nftListing.getUid());
        nftOrder.setBuyer(request.getUid());
        nftOrder.setAssetsId(request.getAssetsId());
        nftOrder.setListingId(request.getListingId());
        nftOrder.setCreatedAt(System.currentTimeMillis());
        nftOrder.setUpdatedAt(nftOrder.getCreatedAt());
        nftOrder.setCurrency(nftListing.getCurrency());
        nftOrder.setPrice(nftListing.getPrice());
        nftOrder.setQuantity(nftListing.getQuantity());
        nftOrder.setStatus(NftOrderStatus.COMPLETE);
        nftOrderDao.insert(nftOrder);

        //change belongs
        nftBelongDao.findAndModify(
                NftBelongQuery.newBuilder().assetsId(nftListing.getAssetsId()).owner(nftOrder.getBuyer()).build(),
                NftBelongUpdate.newBuilder()
                        .quantityInc(nftListing.getQuantity())
                        .createdAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true)
        );

        //todo remove listing reason
        nftListingDao.update(
                NftListingQuery.newBuilder().assetsId(nftListing.getAssetsId()).id(nftListing.getId()).build(),
                NftListingUpdate.newBuilder().canceled(true).build()
        );

        //send events
        sendTransferEvent(nftOrder);
        sendSaleEvent(nftOrder);

        //refresh owner belongs
        refreshPreOwnerStatusAfterBuy(nftListing);

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

            //设置拥有数量
            NftBelong belong = new NftBelong();
            belong.setAssetsId(request.getAssetsId());
            belong.setQuantity(request.getQuantity());
            belong.setOwner(request.getOwner());
            belong.setCreatedAt(System.currentTimeMillis());
            belong.setUpdatedAt(belong.getCreatedAt());
            nftBelongDao.insert(belong);

            //send mint event
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

    private void refreshPreOwnerStatusAfterBuy(NftListing nftListing) {

        //owner
        int preOwnerRestQuantity = 0;
        SimpleQuery preBelongQuery = NftBelongQuery.newBuilder().assetsId(nftListing.getAssetsId()).owner(nftListing.getUid()).build();
        NftBelong preBelong = nftBelongDao.findOne(preBelongQuery);
        if(preBelong.getQuantity() > nftListing.getQuantity()){
            //minus
            int rest = preBelong.getQuantity() - nftListing.getQuantity();
            nftBelongDao.update(preBelongQuery, NftBelongUpdate.newBuilder().setQuantity(rest).build());
            preOwnerRestQuantity = rest;
        }else{
            //del
            nftBelongDao.remove(preBelongQuery);
            preOwnerRestQuantity = 0;
        }

        //refresh listings
        //owner history listings' quantity check and remove
        final int _preOwnerRestQuantity = preOwnerRestQuantity;
        nftListingDao
                .find(NftListingQuery.newBuilder().assetsId(nftListing.getAssetsId()).uid(nftListing.getUid()).canceled(false).build())
                .stream().forEach(history -> {
                    if(history.getQuantity() < _preOwnerRestQuantity){
                        //todo cancel reason
                        nftListingDao.update(
                                NftListingQuery.newBuilder().assetsId(nftListing.getAssetsId()).id(history.getId()).build(),
                                NftListingUpdate.newBuilder().canceled(true).build()
                        );
                    }
                });
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

    private void sendListingEvent(NftListing listing) {

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
        activity.setList(listEvent);

        nftActivityDao.insert(activity);

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

    public NftExchange.ASSETS_EXCHANGE_PROFILE_IS profile(NftExchange.ASSETS_EXCHANGE_PROFILE_IC request) {

        NftExchange.NftAssetsProfileDTO assetsProfileDTO = profile(request.getAssetsId(), false, request.getObserver());
        return NftExchange.ASSETS_EXCHANGE_PROFILE_IS.newBuilder()
                .setProfile(assetsProfileDTO)
                .build();
    }

    /**
     *
     * @param assetsId
     * @param needOwnerUids 是否需要详细的拥有者列表
     * @param abserver 观察者（请求者）
     * @return
     */
    public NftExchange.NftAssetsProfileDTO profile(Long assetsId, boolean needOwnerUids, Long abserver){

        NftExchange.NftAssetsProfileDTO.Builder profileBuilder = NftExchange.NftAssetsProfileDTO.newBuilder();
        profileBuilder.setId(assetsId);

        //floor price
        //todo shijie cache
        Optional<NftListing> floor = nftListingDao
                .find(NftListingQuery.newBuilder().assetsId(assetsId).canceled(false).build())
                .stream().sorted(Comparator.comparingLong(NftListing::getCreatedAt)).min(Comparator.comparingDouble(listing -> listing.getPrice()));
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
        //todo shijie cache
        StringBuffer currency = new StringBuffer();
        Optional<Float> volumeOptional = nftOrderDao.find(NftOrderQuery.newBuilder().assetsId(assetsId).build())
                .stream()
                .map(nftOrder -> {
                    if(currency.isEmpty()){
                        currency.append(nftOrder.getCurrency());
                        //TODO shijie: currency convert
                    }
                    return nftOrder.getPrice() * nftOrder.getQuantity();
                })
                .reduce((a, b) -> a + b);
        if(volumeOptional.isPresent()){
            profileBuilder.setTotalVolume(volumeOptional.get());
            profileBuilder.setCurrency(currency.toString());
        }

        if(!needOwnerUids){
            long owners = nftBelongDao.count(NftBelongQuery.newBuilder().assetsId(assetsId).build());
            profileBuilder.setOwners((int)owners);
        }else{
            profileBuilder.addAllOwnerLists(nftBelongDao.find(NftBelongQuery.newBuilder().assetsId(assetsId).build()).stream().map(NftBelong::getOwner).collect(Collectors.toList()));
        }

        if(null != abserver){
            //
            NftBelong abserverBelong = nftBelongDao.findOne(NftBelongQuery.newBuilder().assetsId(assetsId).owner(abserver).build());
            if(null != abserverBelong){
                profileBuilder.setOwns(abserverBelong.getQuantity());
            }
        }

        return profileBuilder.build();
    }

    public NftExchange.COLLECTION_EXCHANGE_PROFILE_IS profile(NftExchange.COLLECTION_EXCHANGE_PROFILE_IC request){

        NftExchange.NftCollectionProfileDTO.Builder collectionProfileDTOBuilder = NftExchange.NftCollectionProfileDTO.newBuilder();
        Set<Long> ownerLists = new HashSet<>();

        request.getAssetsIdsList().stream().map(assetId -> profile(assetId, true, null)).forEach(profile -> {

            if(!collectionProfileDTOBuilder.hasCurrentListing() || (profile.getCurrentListing().getPrice() < collectionProfileDTOBuilder.getCurrentListing().getPrice())){
                collectionProfileDTOBuilder.setCurrentListing(profile.getCurrentListing());
            }

            if(profile.hasTotalVolume()){
                collectionProfileDTOBuilder.setTotalVolume(collectionProfileDTOBuilder.getTotalVolume() + profile.getTotalVolume());
                collectionProfileDTOBuilder.setCurrency(profile.getCurrency());
            }

            ownerLists.addAll(profile.getOwnerListsList());

        });

        collectionProfileDTOBuilder.setOwners(ownerLists.size());

        return NftExchange.COLLECTION_EXCHANGE_PROFILE_IS.newBuilder().setProfile(collectionProfileDTOBuilder.build()).build();

    }
}
