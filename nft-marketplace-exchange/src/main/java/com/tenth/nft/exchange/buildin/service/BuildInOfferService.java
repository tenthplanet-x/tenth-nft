package com.tenth.nft.exchange.buildin.service;

import com.ruixi.tpulse.convention.protobuf.Search;
import com.ruixi.tpulse.convention.routes.search.SearchUserProfileRouteRequest;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.NftIdModule;
import com.tenth.nft.convention.TpulseHeaders;
import com.tenth.nft.convention.dto.NftUserProfileDTO;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.wallet.WalletPayRouteRequest;
import com.tenth.nft.convention.wallet.WalletMerchantType;
import com.tenth.nft.convention.wallet.WalletOrderBizContent;
import com.tenth.nft.convention.wallet.WalletOrderType;
import com.tenth.nft.convention.wallet.WalletProductCode;
import com.tenth.nft.convention.wallet.utils.WalletTimes;
import com.tenth.nft.exchange.buildin.dto.NftOfferDTO;
import com.tenth.nft.exchange.buildin.vo.*;
import com.tenth.nft.exchange.buildin.wallet.BuildInWalletProvider;
import com.tenth.nft.exchange.common.service.AbsOfferService;
import com.tenth.nft.exchange.common.service.NftActivityService;
import com.tenth.nft.exchange.common.service.NftOrderService;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.NftBelongDao;
import com.tenth.nft.orm.marketplace.dao.NftOfferDao;
import com.tenth.nft.orm.marketplace.dao.expression.*;
import com.tenth.nft.orm.marketplace.entity.*;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.SimpleQuerySorts;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.dao.id.service.GsCollectionIdService;
import com.tpulse.gs.convention.gamecontext.GameUserContext;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class BuildInOfferService extends AbsOfferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildInOfferService.class);

    @Autowired
    private NftOfferDao nftOfferDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private NftActivityService nftActivityService;
    @Autowired
    private GsCollectionIdService gsCollectionIdService;
    @Autowired
    private BuildInWalletProvider buildInWalletProvider;
    @Autowired
    private NftOrderService nftOrderService;

    public Page<NftOfferDTO> list(NftOfferListRequest request) {

        Page<NftOfferDTO> dataPage = nftOfferDao.findPage((SimplePageQuery) NftOfferQuery.newBuilder()
                .assetsId(request.getAssetsId())
                .setPage(request.getPage())
                .setPageSize(request.getPageSize())
                .setSorts(SimpleQuerySorts.newBuilder().sort("price", true).sort("createdAt", true).build())
                .build(),
                NftOfferDTO.class
        );

        if(!dataPage.getData().isEmpty()){

            dataPage.getData().stream()
                    .map(dto -> {
                        dto.setUserProfile(
                                NftUserProfileDTO.from(routeClient.send(
                                        Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(dto.getUid()).build(),
                                        SearchUserProfileRouteRequest.class
                                ).getProfiles(0))
                        );
                        return dto;
                    })
                    .collect(Collectors.toList());
        }

        return dataPage;
    }

    public NftOfferDTO makeOffer(NftMakeOfferRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        preMakeOfferCheck(
                uid,
                request.getAssetsId(),
                request.getQuantity(),
                request.getCurrency(),
                request.getPrice(),
                request.getExpireAt()
        );

        NftOffer nftOffer = new NftOffer();
        nftOffer.setAssetsId(request.getAssetsId());
        nftOffer.setUid(uid);
        nftOffer.setQuantity(request.getQuantity());
        nftOffer.setPrice(request.getPrice());
        nftOffer.setCurrency(request.getCurrency());
        nftOffer.setCreatedAt(System.currentTimeMillis());
        nftOffer.setUpdatedAt(nftOffer.getCreatedAt());
        nftOffer.setExpireAt(request.getExpireAt());
        Long activityId = nftActivityService.sendOfferEvent(nftOffer);
        nftOffer.setActivityId(activityId);
        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(request.getAssetsId())
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();
        nftOffer.setCreatorUid(assetsDTO.getCreator());
        nftOffer.setCreatorFeeRate(assetsDTO.getCreatorFeeRate());
        nftOfferDao.insert(nftOffer);

        NftOfferDTO nftOfferDTO = NftOfferDTO.fromFromEntity(nftOffer);
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


    public void cancel(NftOfferCancelRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);
        NftOffer nftOffer = nftOfferDao.findAndRemove(NftOfferQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getOfferId()).uid(uid).build());
        if(null == nftOffer){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_NOT_EXIST);
        }

        nftActivityService.sendOfferCancelEvent(nftOffer);
    }


    public Long accept(NftOfferAcceptRequest request){

        Long uid = GameUserContext.get().getLong(TpulseHeaders.UID);

        NftOffer nftOffer = nftOfferDao.findOne(NftOfferQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getOfferId()).build());
        preAcceptCheck(
                uid,
                nftOffer
        );

        Long orderId = gsCollectionIdService.incrementAndGet(NftIdModule.EXCHANGE.name());
        Long expiredAt = WalletTimes.getExpiredAt();

        NftOrder order = new NftOrder();
        order.setId(orderId);
        order.setOfferId(nftOffer.getId());
        order.setAssetsId(nftOffer.getAssetsId());
        order.setOwner(uid);
        order.setBuyer(nftOffer.getUid());
        order.setQuantity(nftOffer.getQuantity());
        order.setPrice(nftOffer.getPrice());
        order.setCurrency(nftOffer.getCurrency());
        order.setCreatedAt(System.currentTimeMillis());
        order.setUpdatedAt(order.getCreatedAt());
        order.setStatus(NftOrderStatus.CREATE);
        order.setExpiredAt(expiredAt);
        nftOrderService.insert(order);

        //Get pay channel and token
        WalletOrderBizContent walletOrder = WalletOrderBizContent.newBuilder()
                .activityCfgId(WalletOrderType.NftExpense.getActivityCfgId())
                .productCode(WalletProductCode.NFT_OFFER.name())
                .productId(String.valueOf(nftOffer.getAssetsId()))
                .outOrderId(String.valueOf(orderId))
                .merchantType(WalletMerchantType.PERSONAL.name())
                .merchantId(String.valueOf(uid))
                .currency(nftOffer.getCurrency())
                .value(nftOffer.getPrice())
                .expiredAt(expiredAt)
                .remark("")
                .profits(createProfits(uid, nftOffer))
                .build();
        String token = buildInWalletProvider.createToken(walletOrder);

        //Delegate payment to system
        NftWallet.BillDTO billDTO = routeClient.send(
                NftWallet.BILL_PAY_IC.newBuilder()
                        .setUid(nftOffer.getUid())
                        .setToken(token)
                        .setPassword("")
                        .build(),
                WalletPayRouteRequest.class
        ).getBill();

        return orderId;
    }


    public void expireCheck(NftExchange.OFFER_EXPIRE_CHECK_IC request) {
        nftOfferDao.remove(NftOfferQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getOfferId()).build());
    }

    public NftOrderStatus getAcceptStatus(NftOfferAcceptStatusRequest request) {
        NftOrder nftOrder = nftOrderService.getOrder(request.getAssetsId(), request.getOrderId());
        return nftOrder.getStatus();
    }
}
