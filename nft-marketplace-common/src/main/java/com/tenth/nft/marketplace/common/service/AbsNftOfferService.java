package com.tenth.nft.marketplace.common.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.marketplace.common.dao.AbsNftOfferDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftOfferQuery;
import com.tenth.nft.marketplace.common.dto.NftOfferDTO;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tenth.nft.marketplace.common.entity.AbsNftOrder;
import com.tenth.nft.marketplace.common.entity.AbsNftOffer;
import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tenth.nft.marketplace.common.vo.NftMakeOfferRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferAcceptRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferCancelRequest;
import com.tenth.nft.marketplace.common.vo.NftOfferListRequest;
import com.tenth.nft.marketplace.common.wallet.WalletProviderFactory;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.convention.dao.SimplePageQuery;
import com.tpulse.gs.convention.dao.SimpleQuerySorts;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.router.client.RouteClient;
import com.wallan.router.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shijie
 */
public abstract class AbsNftOfferService<T extends AbsNftOffer>{

    private AbsNftOfferDao<T> nftOfferDao;
    private AbsNftAssetsService nftAssetsService;
    private AbsNftBelongService nftBelongService;
    private AbsNftUbtLogService nftUbtLogService;
    private AbsNftOrderService nftBuyOrderService;
    @Autowired
    private WalletProviderFactory walletProviderFactory;

    private RouteClient routeClient;

    public AbsNftOfferService(
            AbsNftOfferDao<T> nftOfferDao,
            AbsNftAssetsService nftAssetsService,
            AbsNftBelongService nftBelongService,
            AbsNftUbtLogService nftUbtLogService,
            AbsNftOrderService nftBuyOrderService,
            RouteClient routeClient
    ) {
        this.nftOfferDao = nftOfferDao;
        this.nftAssetsService = nftAssetsService;
        this.nftBelongService = nftBelongService;
        this.nftUbtLogService = nftUbtLogService;
        this.nftBuyOrderService = nftBuyOrderService;
        this.routeClient = routeClient;
    }

    public <DTO extends NftOfferDTO> Page<DTO> list(NftOfferListRequest request, Class<DTO> dtoClass) {

        Page<DTO> dataPage = nftOfferDao.findPage((SimplePageQuery) AbsNftOfferQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                        .setSorts(SimpleQuerySorts.newBuilder()
                                .sort("price", true)
                                .sort("createdAt", true)
                                .build()
                        )
                        .build(),
                dtoClass
        );

//        if(!dataPage.getData().isEmpty()){
//            dataPage.getData().stream()
//                    .map(dto -> {
//                        dto.setUserProfile(
//                                NftUserProfileDTO.from(routeClient.send(
//                                        Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(dto.getUid()).build(),
//                                        SearchUserProfileRouteRequest.class
//                                ).getProfiles(0))
//                        );
//                        return dto;
//                    })
//                    .collect(Collectors.toList());
//        }

        return dataPage;
    }

    public NftOfferDTO makeOffer(String buyer, NftMakeOfferRequest request){

        AbsNftAssets nftAssets = nftAssetsService.findOne(request.getAssetsId());
        preMakeOfferCheck(
                buyer,
                request,
                nftAssets
        );

        T nftOffer = newNftOffer();
        nftOffer.setAssetsId(request.getAssetsId());
        nftOffer.setBuyer(buyer);
        nftOffer.setQuantity(request.getQuantity());
        nftOffer.setPrice(new BigDecimal(request.getPrice()));
        nftOffer.setCurrency(request.getCurrency());
        nftOffer.setCreatedAt(System.currentTimeMillis());
        nftOffer.setUpdatedAt(nftOffer.getCreatedAt());
        nftOffer.setExpireAt(request.getExpireAt());
        Long activityId = nftUbtLogService.sendOfferEvent(nftOffer);
        nftOffer.setActivityId(activityId);
        nftOffer.setCreator(nftAssets.getCreator());
        nftOffer.setCreatorFeeRate(nftAssets.getCreatorFeeRate());
        nftOffer.setSignature(request.getSignature());
        nftOffer = nftOfferDao.insert(nftOffer);

        NftOfferDTO nftOfferDTO = toDTO(nftOffer);
//        nftOfferDTO.setUserProfile(
//                NftUserProfileDTO.from(
//                        routeClient.send(
//                                Search.SEARCH_USER_PROFILE_IC.newBuilder().addUids(uid).build(),
//                                SearchUserProfileRouteRequest.class
//                        ).getProfiles(0)
//                )
//        );

        return nftOfferDTO;

    }

    public void cancel(String buyer, NftOfferCancelRequest request){

        AbsNftOffer nftOffer = nftOfferDao.findAndRemove(AbsNftOfferQuery
                .newBuilder()
                .assetsId(request.getAssetsId())
                .id(request.getOfferId())
                .buyer(buyer)
                .build()
        );
        if(null == nftOffer){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_NOT_EXIST);
        }

        nftUbtLogService.sendOfferCancelEvent(nftOffer);
    }

    public AbsNftOrder accept(String seller, NftOfferAcceptRequest request){

        AbsNftAssets nftAssets = nftAssetsService.findOne(request.getAssetsId());
        T nftOffer = nftOfferDao.findOne(AbsNftOfferQuery.newBuilder()
                .assetsId(request.getAssetsId())
                .id(request.getOfferId())
                .build());
        preAcceptCheck(
                seller,
                nftOffer,
                nftAssets
        );

        AbsNftOrder nftBuyOrder = nftBuyOrderService.create(
                nftOffer.getBuyer(),
                NftOuterProduct.newBuilder()
                        .outerOrderId(String.valueOf(nftOffer.getId()))
                        .seller(seller)
                        .price(nftOffer.getPrice().toString())
                        .quantity(nftOffer.getQuantity())
                        .currency(nftOffer.getCurrency())
                        .build(),
                nftAssets
        );

        //afterCreateAcceptOrder(seller, nftAssets, nftOffer, nftBuyOrder);

        return nftBuyOrder;
    }

    //protected void afterCreateAcceptOrder(String seller, AbsNftAssets nftAssets, T nftOffer, AbsNftOrder nftBuyOrder){};

    protected NftOfferDTO toDTO(T nftOffer) {
        NftOfferDTO dto = newDTO();
        dto.setId(nftOffer.getId());
        dto.setBuyer(nftOffer.getBuyer());
        dto.setAssetsId(nftOffer.getAssetsId());
        dto.setQuantity(nftOffer.getQuantity());
        dto.setPrice(nftOffer.getPrice().toString());
        dto.setCurrency(nftOffer.getCurrency());
        dto.setCreatedAt(nftOffer.getCreatedAt());
        dto.setExpireAt(nftOffer.getExpireAt());
        return dto;
    }

    protected NftOfferDTO newDTO() {
        return new NftOfferDTO();
    }

    protected abstract T newNftOffer();

    protected void preMakeOfferCheck(
            String buyer,
            NftMakeOfferRequest request,
            AbsNftAssets assets

    ) {

        if(assets.getSupply() < request.getQuantity()){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INVALID_PARAMS);
        }
        if(Times.isExpired(request.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_INVALID_PARAMS);
        }
        int owns = nftBelongService.owns(assets.getId(), buyer);
        if(owns == assets.getSupply()){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_BELONGS_TO_YOU);
        }
    }

    protected void preAcceptCheck(String seller, AbsNftOffer nftOffer, AbsNftAssets nftAssets) {

        if(null == nftOffer){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_NOT_EXIST);
        }
        if(Times.isExpired(nftOffer.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.OFFER_EXCEPTION_EXPIRED);
        }
        if(nftOffer.getBuyer().equals(seller)){
            throw BizException.newInstance(NftExchangeErrorCodes.ACCEPT_EXCEPTION_OWNS);
        }
    }

    protected List<WalletOrderBizContent.Profit> createProfits(String seller, T nftOffer) {

        List<WalletOrderBizContent.Profit> profits = new ArrayList<>();

        BigDecimal profitValue = nftOffer.getPrice();
        BigDecimal creatorFee = BigDecimal.ZERO;
        if(!Strings.isNullOrEmpty(nftOffer.getCreatorFeeRate())){
            creatorFee = profitValue.multiply(new BigDecimal(nftOffer.getCreatorFeeRate()).divide(new BigDecimal(100)));
            profitValue = profitValue.subtract(creatorFee);
        }
        //seller
        {
            WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
            profit.setActivityCfgId(WalletOrderType.NftIncome.getActivityCfgId());
            profit.setCurrency(nftOffer.getCurrency());
            profit.setValue(profitValue.toString());
            profit.setTo(seller);
            profits.add(profit);
        }
        //creator fee
        {
            if(creatorFee.compareTo(BigDecimal.ZERO) > 0){
                WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
                profit.setActivityCfgId(WalletOrderType.CreatorIncome.getActivityCfgId());
                profit.setCurrency(nftOffer.getCurrency());
                profit.setValue(creatorFee.toString());
                profit.setTo(nftOffer.getCreator());
                profits.add(profit);
            }
        }
        //service fee

        return profits;

    }

    protected T findOne(Long assetsId, Long offerId) {
        return nftOfferDao.findOne(AbsNftOfferQuery.newBuilder()
                .assetsId(assetsId)
                .id(offerId)
                .build());
    }

    public NftExchange.PAYMENT_RECEIVE_IS receiveReceipt(NftExchange.PAYMENT_RECEIVE_IC request) {

        NftExchange.PAYMENT_RECEIVE_IS.Builder builder = NftExchange.PAYMENT_RECEIVE_IS.newBuilder();

        Long orderId = Long.valueOf(request.getOrderId());
        //SimpleQuery orderQuery = NftOrderQuery.newBuilder().assetsId(request.getAssetsId()).id(orderId).build();
        AbsNftOrder nftOrder = nftBuyOrderService.findOne(request.getAssetsId(), orderId);
        if(null != nftOrder && NftOrderStatus.CREATE.equals(nftOrder.getStatus())){
            Long offerId = Long.valueOf(nftOrder.getOuterOrderId());
            WalletBillState state = WalletBillState.valueOf(request.getState());
            switch (state){
                case PAYED:
                    int owns = nftBelongService.owns(nftOrder.getAssetsId(), nftOrder.getSeller());
                    if(owns < nftOrder.getQuantity()){
                        //throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_ENOUGH_QUANTITY);
                        nftBuyOrderService.updateStatus(
                                nftOrder.getAssetsId(),
                                nftOrder.getId(),
                                NftOrderStatus.CANCEL,
                                "Doesn't have enough assets"
                        );
                    }else{
                        nftBuyOrderService.updateStatus(nftOrder.getAssetsId(), nftOrder.getId(), NftOrderStatus.COMPLETE);
                        doTransfer(nftOrder);
                    }
//                    removeListing(nftOrder.getAssetsId(), Long.valueOf(nftOrder.getOuterOrderId()));
//                    refreshListingsBelongTo(nftOrder.getAssetsId(), nftOrder.getSeller());
                    remove(nftOrder.getAssetsId(), offerId);
                    builder.setRefund(false);
                    break;
                case FAIL:
                    nftBuyOrderService.updateStatus(
                            nftOrder.getAssetsId(),
                            nftOrder.getId(),
                            NftOrderStatus.CANCEL,
                            "Pay failed"
                    );
                    builder.setRefund(false);
                    break;
            }
            //TODO Unknown stage
        }

        return builder.build();
    }

    protected AbsNftOffer remove(Long assetsId, Long offerId) {
        AbsNftOffer nftOffer = nftOfferDao.findAndRemove(AbsNftOfferQuery.newBuilder()
                .assetsId(assetsId)
                .id(offerId)
                .build());
        nftUbtLogService.freezeOfferEvent(nftOffer);
        return nftOffer;
    }

    protected void doTransfer(AbsNftOrder nftOrder) {

        //Change(inc) the quantity of assets belongs to buyer
        nftBelongService.inc(nftOrder.getAssetsId(), nftOrder.getBuyer(), nftOrder.getQuantity());
        //Change(dec) the quantity of assets belongs to buyer
        nftBelongService.dec(nftOrder.getAssetsId(), nftOrder.getSeller(), nftOrder.getQuantity());
        //Create events
        nftUbtLogService.sendTransferEvent(nftOrder);
        nftUbtLogService.sendSaleEvent(nftOrder);
        //Send events to stats
        //TODO
        //sendExchangeRouteEventToStats(nftOrder.getAssetsId());
    }



}
