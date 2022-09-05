package com.tenth.nft.exchange.buildin.controller.routes;

import com.tenth.nft.convention.routes.exchange.*;
import com.tenth.nft.exchange.buildin.service.BuildInListingService;
import com.tenth.nft.exchange.common.service.NftActivityService;
import com.tenth.nft.exchange.common.service.NftBelongService;
import com.tenth.nft.exchange.common.service.NftListingService;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.requestmapping.annotation.RouteRequestMapping;
import com.wallan.router.annotation.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
@Route
public class NftExchangeRoutesController {

    @Autowired
    private BuildInListingService nftExchangeService;
    @Autowired
    private NftListingService nftListingService;
    @Autowired
    private NftBelongService nftOwnerService;

    @RouteRequestMapping(PaymentReceiveRouteRequest.class)
    public NftExchange.PAYMENT_RECEIVE_IS receivePayment(NftExchange.PAYMENT_RECEIVE_IC request){
        return nftExchangeService.receivePayment(request);
    }

    @RouteRequestMapping(ListingListRouteRequest.class)
    public NftExchange.LISTING_LIST_IS listingList(NftExchange.LISTING_LIST_IC request){
        return nftListingService.list(request);
    }

    @RouteRequestMapping(AssetsExchangeProfileRouteRequest.class)
    public NftExchange.ASSETS_EXCHANGE_PROFILE_IS assetsProfile(NftExchange.ASSETS_EXCHANGE_PROFILE_IC request){
        return nftExchangeService.profile(request);
    }

    @RouteRequestMapping(CollectionsExchangeProfileRouteRequest.class)
    public NftExchange.COLLECTION_EXCHANGE_PROFILE_IS collectionProfile(NftExchange.COLLECTION_EXCHANGE_PROFILE_IC request){
        return nftExchangeService.profile(request);
    }

    @RouteRequestMapping(ListingExpireCheckRouteRequest.class)
    public void expireCheck(NftExchange.LISTING_EXPIRE_CHECK_IC request){
        nftExchangeService.expireCheck(request);
    }


}
