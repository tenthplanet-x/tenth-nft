package com.tenth.nft.marketplace.common.service;

import com.google.common.base.Strings;
import com.tenth.nft.convention.NftExchangeErrorCodes;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.marketplace.common.dao.AbsNftListingDao;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftListingQuery;
import com.tenth.nft.marketplace.common.dto.NftListingDTO;
import com.tenth.nft.marketplace.common.dto.NftWalletPayTicket;
import com.tenth.nft.marketplace.common.entity.AbsNftAssets;
import com.tenth.nft.marketplace.common.entity.AbsNftBuyOrder;
import com.tenth.nft.marketplace.common.entity.AbsNftListing;
import com.tenth.nft.marketplace.common.entity.NftOrderStatus;
import com.tenth.nft.marketplace.common.entity.event.ListCancelEventReason;
import com.tenth.nft.marketplace.common.vo.NftListingBuyRequest;
import com.tenth.nft.marketplace.common.vo.NftListingCancelRequest;
import com.tenth.nft.marketplace.common.vo.NftListingListRequest;
import com.tenth.nft.marketplace.common.vo.NftListingCreateRequest;
import com.tenth.nft.marketplace.common.wallet.IWalletProvider;
import com.tenth.nft.marketplace.common.wallet.WalletProviderFactory;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.convention.dao.dto.Page;
import com.wallan.router.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
public abstract class AbsNftListingService<T extends AbsNftListing>{

    private Logger LOGGER = LoggerFactory.getLogger(AbsNftListingService.class);

    private AbsNftAssetsService nftAssetsService;
    private AbsNftListingDao<T> nftListingDao;
    private AbsNftBelongService nftBelongService;
    private AbsNftUbtLogService nftUbtLogService;
    private AbsNftBuyOrderService nftBuyOrderService;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private WalletProviderFactory walletProviderFactory;


    public AbsNftListingService(
            AbsNftAssetsService nftAssetsService,
            AbsNftListingDao<T> nftListingDao,
            AbsNftBelongService nftBelongService,
            AbsNftUbtLogService nftUbtLogService,
            AbsNftBuyOrderService nftBuyOrderService
    ) {
        this.nftAssetsService = nftAssetsService;
        this.nftListingDao = nftListingDao;
        this.nftBelongService = nftBelongService;
        this.nftUbtLogService = nftUbtLogService;
        this.nftBuyOrderService = nftBuyOrderService;
    }

    /**
     * Create Listing
     * @param seller
     * @param request
     * @return
     */
    public NftListingDTO create(String seller, NftListingCreateRequest request){

        AbsNftAssets assets = nftAssetsService.findById(request.getAssetsId());
        preListingCheck(seller, request, assets);

        T listing = buildEntity(seller, request, assets);
        listing = nftListingDao.insert(listing);
        return toDTO(listing);
    }

    /**
     * Buy
     * @param buyer
     * @param request
     * @return
     */
    public NftWalletPayTicket buy(String buyer, NftListingBuyRequest request) {

        //listing check
        T nftListing = nftListingDao.findOne(
                AbsNftListingQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .id(request.getListingId())
                        .build()
        );
        AbsNftAssets nftAssets = nftAssetsService.findById(nftListing.getAssetsId());
        preBuyCheck(buyer, request, nftListing, nftAssets);

        //Create order
        AbsNftBuyOrder order = nftBuyOrderService.create(buyer, nftListing, nftAssets);

        //Create pay content
        IWalletProvider walletProvider = walletProviderFactory.get(nftAssets.getBlockchain());
        String content = createPayContent(buyer, nftListing, nftAssets, walletProvider, order);

        NftWalletPayTicket response = new NftWalletPayTicket();
        response.setContent(content);
        response.setCurrency(nftListing.getCurrency());
        response.setValue(nftListing.getPrice());

        return response;

    }

    public NftExchange.PAYMENT_RECEIVE_IS receivePayment(NftExchange.PAYMENT_RECEIVE_IC request){

        NftExchange.PAYMENT_RECEIVE_IS.Builder builder = NftExchange.PAYMENT_RECEIVE_IS.newBuilder();

        Long orderId = Long.valueOf(request.getOrderId());
        //SimpleQuery orderQuery = NftOrderQuery.newBuilder().assetsId(request.getAssetsId()).id(orderId).build();
        AbsNftBuyOrder nftOrder = nftBuyOrderService.findOne(request.getAssetsId(), orderId);
        if(null != nftOrder && NftOrderStatus.CREATE.equals(nftOrder.getStatus())){
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
                    removeListing(nftOrder.getAssetsId(), nftOrder.getListingId());
                    refreshListingsBelongTo(nftOrder.getAssetsId(), nftOrder.getSeller());
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

    public <T extends NftListingDTO> Page<T> list(NftListingListRequest request, Class<T> dtoClass) {

        return nftListingDao.findPage(
                AbsNftListingQuery.newBuilder()
                        .assetsId(request.getAssetsId())
                        .setPage(request.getPage())
                        .setPageSize(request.getPageSize())
                .build(),
                dtoClass
        );

//        List<NftListingDTO> data = routeClient.send(
//                NftExchange.LISTING_LIST_IC.newBuilder()
//                        .setAssetsId(request.getAssetsId())
//                        .setPage(request.getPage())
//                        .setPageSize(request.getPageSize())
//                        .build(),
//                ListingListRouteRequest.class
//        ).getListingsList().stream().map(NftListingDTO::from).collect(Collectors.toList());
//
//        //fill with userProfile
//        Collection<Long> sellerUids = data.stream().map(dto -> dto.getSeller()).collect(Collectors.toSet());
//        Map<Long, UserProfileDTO> userProfileDTOMap = routeClient.send(
//                Search.SEARCH_USER_PROFILE_IC.newBuilder().addAllUids(sellerUids).build(),
//                SearchUserProfileRouteRequest.class
//        ).getProfilesList().stream().map(NftListingService::from).collect(Collectors.toMap(UserProfileDTO::getUid, Function.identity()));
//        data.stream().forEach(dto -> {
//            dto.setSellerProfile(userProfileDTOMap.get(dto.getSeller()));
//        });

//        return new Page<>(0, data);
    }

    public void cancel(String seller, NftListingCancelRequest request) {
        AbsNftListing nftListing = nftListingDao.findAndRemove(
                AbsNftListingQuery.newBuilder().assetsId(request.getAssetsId()).id(request.getListingId()).seller(seller).build()
        );
        if(null == nftListing){
            throw BizException.newInstance(NftExchangeErrorCodes.LISTING_CANCEL_EXCEPTION_NOT_EXIST);
        }
        nftUbtLogService.createCancelEvent(nftListing, request.getReason());
//        sendListingRouteEventToStats(assetsId);
//        sendListingRouteEventToSearch(nftListing.getAssetsId());
    }

    protected void preBuyCheck(String buyer, NftListingBuyRequest request, AbsNftListing nftListing, AbsNftAssets nftAssets) {

        if(null == nftListing){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NO_EXIST);
        }
        if(Times.earlierThan(nftListing.getStartAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_NOT_START);
        }
        if(Times.isExpired(nftListing.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_EXPIRED);
        }
        if(nftListing.getSeller().equals(buyer)){
            throw BizException.newInstance(NftExchangeErrorCodes.BUY_EXCEPTION_BELONGS_TO_YOU);
        }

        //TODO check current owns subtract orders does not complete

    }

    protected T buildEntity(String seller, NftListingCreateRequest request, AbsNftAssets assets) {

        T listing = newListingEntity();
        listing.setSeller(seller);
        listing.setAssetsId(request.getAssetsId());
        listing.setQuantity(request.getQuantity());
        listing.setPrice(request.getPrice());
        listing.setCurrency(request.getCurrency());
        listing.setStartAt(request.getStartAt());
        listing.setExpireAt(request.getExpireAt());
        listing.setCreatedAt(System.currentTimeMillis());
        listing.setUpdatedAt(listing.getCreatedAt());
        listing.setCreator(assets.getCreator());
        listing.setCreatorFeeRate(assets.getCreatorFeeRate());
        listing.setSignature(request.getSignature());

        return listing;
    }

    protected void preListingCheck(String seller, NftListingCreateRequest request, AbsNftAssets assets) throws BizException {
        if(Times.isExpired(request.getExpireAt())){
            throw BizException.newInstance(NftExchangeErrorCodes.LISTING_EXCEPTION_INVALID_PARAMS);
        }
        //Quantity check
        int owns = nftBelongService.owns(assets.getId(), seller);
        if(owns < request.getQuantity()){
            throw BizException.newInstance(NftExchangeErrorCodes.LISTING_BUY_EXPCETION_NO_ENOUGH_ASSETS);
        }
    }

    protected abstract T newListingEntity();

    protected NftListingDTO toDTO(T listing) {

        NftListingDTO dto = new NftListingDTO();
        dto.setId(listing.getId());
        dto.setAssetsId(listing.getAssetsId());
        dto.setCurrency(listing.getCurrency());
        dto.setPrice(listing.getPrice());
        dto.setQuantity(listing.getQuantity());
        dto.setSeller(listing.getSeller());
        dto.setStartAt(listing.getStartAt());
        dto.setExpireAt(listing.getExpireAt());
        dto.setCreatedAt(listing.getCreatedAt());
        return dto;
    }

    protected String createPayContent(
            String buyer,
            T nftListing,
            AbsNftAssets nftAssets,
            IWalletProvider walletProvider,
            AbsNftBuyOrder nftBuyOrder) {
        WalletOrderBizContent payContent = WalletOrderBizContent.newBuilder()
                .activityCfgId(WalletOrderType.NftExpense.getActivityCfgId())
                .productCode(WalletProductCode.NFT.name())
                .productId(String.valueOf(nftListing.getAssetsId()))
                .outOrderId(String.valueOf(nftBuyOrder.getId()))
                .merchantType(WalletMerchantType.PERSONAL.name())
                .merchantId(nftListing.getSeller())
                .currency(nftListing.getCurrency())
                .value(nftListing.getPrice())
                .expiredAt(nftBuyOrder.getExpiredAt())
                .remark("")
                .profits(createProfits(nftListing))
                .build();
        String token = walletProvider.createToken(payContent);
        return token;
    }

    protected List<WalletOrderBizContent.Profit> createProfits(T nftListing) {

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
            profit.setTo(nftListing.getSeller());
            profits.add(profit);
        }
        //creator fee
        {
            if(creatorFee.compareTo(BigDecimal.ZERO) > 0){
                WalletOrderBizContent.Profit profit = new WalletOrderBizContent.Profit();
                profit.setActivityCfgId(WalletOrderType.CreatorIncome.getActivityCfgId());
                profit.setCurrency(nftListing.getCurrency());
                profit.setValue(creatorFee.toString());
                profit.setTo(nftListing.getCreator());
                profits.add(profit);
            }
        }
        //service fee

        return profits;

    }

    protected void doTransfer(AbsNftBuyOrder nftOrder) {

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

    protected AbsNftListing removeListing(Long assetsId, Long listingId) {
        AbsNftListing nftListing = nftListingDao.findAndRemove(
                AbsNftListingQuery.newBuilder().assetsId(assetsId).id(listingId).build()
        );
        nftUbtLogService.freezeListingEvent(nftListing.getActivityId());
        return nftListing;
    }

    protected void refreshListingsBelongTo(Long assetsId, String owner) {

        int rest = nftBelongService.owns(assetsId, owner);
        //
        final int _rest = rest;
        List<Long> events = nftListingDao
                .find(AbsNftListingQuery.newBuilder().assetsId(assetsId).seller(owner).build())
                .stream().filter(entity -> entity.getQuantity() > _rest).map(entity -> entity.getId()).filter(Objects::nonNull).collect(Collectors.toList());
        if(!events.isEmpty()){
            cancelBatch(assetsId, events, ListCancelEventReason.QUANTITY.name());
        }
    }

    public void cancelBatch(Long assetsId, List<Long> listingIds, String reason){
        for(Long listingId: listingIds){
            AbsNftListing nftListing = removeListing(assetsId, listingId);
            nftUbtLogService.createCancelEvent(nftListing, reason);
        }
    }

}
