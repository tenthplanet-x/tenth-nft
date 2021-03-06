package com.tenth.nft.exchange.controller.routes;

import com.tenth.nft.convention.routes.exchange.*;
import com.tenth.nft.exchange.service.NftActivityService;
import com.tenth.nft.exchange.service.NftExchangeService;
import com.tenth.nft.exchange.service.NftListingService;
import com.tenth.nft.exchange.service.NftOwnerService;
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
    private NftExchangeService nftExchangeService;
    @Autowired
    private NftListingService nftListingService;
    @Autowired
    private NftActivityService nftActivityService;
    @Autowired
    private NftOwnerService nftOwnerService;

    @RouteRequestMapping(MintRouteRequest.class)
    public NftExchange.MINT_IS mint(NftExchange.MINT_IC request){
        return nftExchangeService.mint(request);
    }

    @RouteRequestMapping(SellRouteRequest.class)
    public NftExchange.SELL_IS sell(NftExchange.SELL_IC request){
        NftExchange.NftListingDTO listingDTO = nftExchangeService.sell(request);
        return NftExchange.SELL_IS.newBuilder()
                .setListing(listingDTO)
                .build();
    }

    @RouteRequestMapping(SellCancelRouteRequest.class)
    public NftExchange.SELL_CANCEL_IS sellCancel(NftExchange.SELL_CANCEL_IC request){
        return nftExchangeService.sellCancel(request);
    }

    @RouteRequestMapping(BuyRouteRequest.class)
    public NftExchange.BUY_IS buy(NftExchange.BUY_IC request){
        nftExchangeService.buy(request);
        return NftExchange.BUY_IS.newBuilder().build();
    }

    @RouteRequestMapping(ListingListRouteRequest.class)
    public NftExchange.LISTING_LIST_IS listinglist(NftExchange.LISTING_LIST_IC request){
        return nftListingService.list(request);
    }

    @RouteRequestMapping(ActivityListRouteRequest.class)
    public NftExchange.ACTIVITY_LIST_IS activityList(NftExchange.ACTIVITY_LIST_IC request){
        return nftActivityService.list(request);
    }

    @RouteRequestMapping(OwnerListRouteRequest.class)
    public NftExchange.OWNER_LIST_IS ownerList(NftExchange.OWNER_LIST_IC request){
        return nftOwnerService.list(request);
    }

    @RouteRequestMapping(AssetsExchangeProfileRouteRequest.class)
    public NftExchange.ASSETS_EXCHANGE_PROFILE_IS assetsProfile(NftExchange.ASSETS_EXCHANGE_PROFILE_IC request){
        return nftExchangeService.profile(request);
    }

    @RouteRequestMapping(CollectionsExchangeProfileRouteRequest.class)
    public NftExchange.COLLECTION_EXCHANGE_PROFILE_IS collectionProfile(NftExchange.COLLECTION_EXCHANGE_PROFILE_IC request){
        return nftExchangeService.profile(request);
    }

}
