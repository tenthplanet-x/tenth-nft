package com.tenth.nft.exchange.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.exchange.OfferAcceptRouteRequest;
import com.tenth.nft.convention.routes.exchange.OfferCancelRouteRequest;
import com.tenth.nft.convention.routes.exchange.OfferMakeRouteRequest;
import com.tenth.nft.convention.routes.exchange.OfferListRouteRequest;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.exchange.dto.NftOfferDTO;
import com.tenth.nft.exchange.vo.NftMakeOfferRequest;
import com.tenth.nft.exchange.vo.NftOfferAcceptRequest;
import com.tenth.nft.exchange.vo.NftOfferCancelRequest;
import com.tenth.nft.exchange.vo.NftOfferListRequest;
import com.tenth.nft.orm.marketplace.dao.NftActivityDao;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.NftBelongDao;
import com.tenth.nft.orm.marketplace.dao.NftOfferDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftOfferQuery;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.orm.marketplace.entity.event.ListCancelEventReason;
import com.tenth.nft.orm.marketplace.entity.event.OfferEvent;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class NftOfferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NftOfferService.class);

    @Autowired
    private NftOfferDao nftOfferDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftBelongDao nftBelongDao;
    @Autowired
    private NftAssetsDao nftAssetsDao;
    @Autowired
    private NftActivityDao nftActivityDao;
    @Autowired
    private NftExchangeService nftExchangeService;

    public Page<NftOfferDTO> list(NftOfferListRequest request) {

        NftExchange.OFFER_LIST_IS response = routeClient.send(
                NftExchange.OFFER_LIST_IC.newBuilder()
                        .setAssetsId(request.getAssetsId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .build(),
                OfferListRouteRequest.class
        );

        if(!response.getOffersList().isEmpty()){
            return new Page<>(
                    0,
                    response.getOffersList().stream().map(NftOfferDTO::from)
                            .map(dto -> {
                                //user profile
                                dto.setUserProfile(
                                        NftUserProfileDTO.from(routeClient.send(
                                                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(dto.getUid()).build(),
                                                SearchUserProfileRouteRequest.class
                                        ).getProfiles(0))
                                );
                                return dto;
                            })
                            .collect(Collectors.toList())
            );
        }

        return new Page<>();
    }


    public NftExchange.OFFER_LIST_IS offerList(NftExchange.OFFER_LIST_IC request){

        Page<NftOffer> dataPage = nftOfferDao.findPage(NftOfferQuery.newBuilder()
                .assetsId(request.getAssetsId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSortField("createdAt")
                        .setReverse(true)
                .build()
        );


        if(!dataPage.getData().isEmpty()){
            return NftExchange.OFFER_LIST_IS.newBuilder()
                    .addAllOffers(dataPage.getData().stream().map(NftOfferDTO::from).collect(Collectors.toList()))
                    .build();
        }

        return NftExchange.OFFER_LIST_IS.newBuilder().build();
    }

    public NftOfferDTO makeOffer(NftMakeOfferRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftExchange.NftOfferDTO offerDTO = routeClient.send(
                NftExchange.OFFER_MAKE_IC.newBuilder()
                        .setUid(uid)
                        .setAssetsId(request.getAssetsId())
                        .setExpireAt(request.getExpireAt())
                        .setQuantity(request.getQuantity())
                        .setPrice(request.getPrice())
                        .setCurrency(request.getCurrency())
                        .build(),
                OfferMakeRouteRequest.class
        ).getOffer();

        NftOfferDTO nftOfferDTO = NftOfferDTO.from(offerDTO);
        nftOfferDTO.setUserProfile(
                NftUserProfileDTO.from(
                        routeClient.send(
                                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(uid).build(),
                                SearchUserProfileRouteRequest.class
                        ).getProfiles(0)
                )
        );

        return nftOfferDTO;

    }

    public NftExchange.OFFER_MAKE_IS makeOffer(NftExchange.OFFER_MAKE_IC request){

        NftAssets assets = nftAssetsDao.findOne(NftAssetsQuery.newBuilder().id(request.getAssetsId()).build());
        if(assets.getSupply() < request.getQuantity()){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INSUFFICIENT);
        }

        //todo wallet check

        NftOffer nftOffer = new NftOffer();
        nftOffer.setAssetsId(request.getAssetsId());
        nftOffer.setUid(request.getUid());
        nftOffer.setQuantity(request.getQuantity());
        nftOffer.setPrice(request.getPrice());
        nftOffer.setCurrency(request.getCurrency());
        nftOffer.setCreatedAt(System.currentTimeMillis());
        nftOffer.setUpdatedAt(nftOffer.getCreatedAt());
        nftOffer.setExpireAt(request.getExpireAt());
        nftOfferDao.insert(nftOffer);

        sendOfferEvent(nftOffer);

        return NftExchange.OFFER_MAKE_IS.newBuilder().setOffer(NftOfferDTO.from(nftOffer)).build();

    }

    private void sendOfferEvent(NftOffer nftOffer) {

        NftActivity nftActivity = new NftActivity();
        nftActivity.setAssetsId(nftOffer.getAssetsId());
        nftActivity.setType(NftActivityEventType.OFFER);
        nftActivity.setCreatedAt(System.currentTimeMillis());
        nftActivity.setUpdatedAt(nftActivity.getCreatedAt());

        OfferEvent offerEvent = new OfferEvent();
        offerEvent.setFrom(nftOffer.getUid());
        offerEvent.setQuantity(nftOffer.getQuantity());
        offerEvent.setPrice(nftOffer.getPrice());
        offerEvent.setCurrency(nftOffer.getCurrency());
        nftActivity.setOffer(offerEvent);

        nftActivityDao.insert(nftActivity);
    }


    public void cancel(NftOfferCancelRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        routeClient.send(
                NftExchange.OFFER_CANCEL_IC.newBuilder()
                        .setUid(uid)
                        .setAssetsId(request.getAssetsId())
                        .setOfferId(request.getOfferId())
                        .build(),
                OfferCancelRouteRequest.class
        );

    }

    public void cancel(NftExchange.OFFER_CANCEL_IC request){

        NftOffer nftOffer = nftOfferDao.findAndRemove(NftOfferQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getOfferId()).uid(request.getUid()).build());
        if(null == nftOffer){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_NOT_EXIST);
        }

        NftActivity nftActivity = new NftActivity();
        nftActivity.setAssetsId(nftOffer.getAssetsId());
        nftActivity.setType(NftActivityEventType.OFFER_CANCEL);
        nftActivity.setCreatedAt(System.currentTimeMillis());
        nftActivity.setUpdatedAt(nftActivity.getCreatedAt());

        OfferEvent offerEvent = new OfferEvent();
        offerEvent.setFrom(nftOffer.getUid());
        offerEvent.setQuantity(nftOffer.getQuantity());
        offerEvent.setPrice(nftOffer.getPrice());
        offerEvent.setCurrency(nftOffer.getCurrency());
        offerEvent.setCancel(true);
        nftActivity.setOffer(offerEvent);

        nftActivityDao.insert(nftActivity);

    }

    public void accept(NftOfferAcceptRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        routeClient.send(
                NftExchange.OFFER_ACCEPT_IC.newBuilder()
                        .setUid(uid)
                        .setAssetsId(request.getAssetsId())
                        .setOfferId(request.getOfferId())
                        .build(),
                OfferAcceptRouteRequest.class
        );

    }

    public void accept(NftExchange.OFFER_ACCEPT_IC request){

        NftOffer nftOffer = nftOfferDao.findOne(NftOfferQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getOfferId()).build());
        if(null == nftOffer){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_NOT_EXIST);
        }
        if(Times.isExpired(nftOffer.getExpireAt())){
            cancel(NftExchange.OFFER_CANCEL_IC.newBuilder()
                    .setUid(nftOffer.getUid())
                    .setAssetsId(request.getAssetsId())
                    .setOfferId(request.getOfferId())
                    .setReason(ListCancelEventReason.EXPIRED.name())
                    .build());
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_OFFER_EXPIRED);
        }

        NftAssets nftAssets = nftAssetsDao.findOne(NftAssetsQuery.newBuilder().id(request.getAssetsId()).build());

        //buy
        try{
            NftOrder order = new NftOrder();
            order.setOfferId(nftOffer.getId());
            order.setAssetsId(nftOffer.getAssetsId());
            order.setOwner(request.getUid());
            order.setBuyer(nftOffer.getUid());
            order.setQuantity(nftOffer.getQuantity());
            order.setPrice(nftOffer.getPrice());
            order.setCurrency(nftOffer.getCurrency());
            order.setCreatedAt(System.currentTimeMillis());
            order.setUpdatedAt(order.getCreatedAt());
            nftExchangeService.buy(order);
            nftOfferDao.remove(NftOfferQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getOfferId()).build());
        }finally {
            //
        }

    }



}
