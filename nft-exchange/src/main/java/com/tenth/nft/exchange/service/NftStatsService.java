package com.tenth.nft.exchange.service;

import com.tenth.nft.convention.routes.search.AssetsRouteRequest;
import com.tenth.nft.convention.routes.search.CurrencyRatesRouteRequest;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.orm.marketplace.dao.NftAssetsDao;
import com.tenth.nft.orm.marketplace.dao.NftAssetsStatsDao;
import com.tenth.nft.orm.marketplace.dao.NftListingDao;
import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsStatsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsStatsUpdate;
import com.tenth.nft.orm.marketplace.dao.expression.NftListingQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderQuery;
import com.tenth.nft.orm.marketplace.entity.NftAssetsStats;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.router.client.RouteClient;
import org.bouncycastle.jcajce.provider.digest.RIPEMD128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author shijie
 */
@Service
public class NftStatsService {

    @Autowired
    private NftOrderDao nftOrderDao;
    @Autowired
    private NftListingDao nftListingDao;
    @Autowired
    private NftAssetsStatsDao nftAssetsStatsDao;
    @Autowired
    private RouteClient routeClient;

    public void exchangeEventHandle(NftExchange.EXCHANGE_EVENT_IC request) {

        Long assetsId = request.getAssetsId();
        //get assetsProfile
        NftSearch.AssetsDTO assetsProfile = routeClient.send(
                NftSearch.ASSETS_IC.newBuilder().setAssetsId(assetsId).build(),
                AssetsRouteRequest.class
        ).getAssets();
        String currency = assetsProfile.getCurrency();

        //currencyRates;
        Map<String, Float> currencyRates = routeClient.send(
                NftSearch.NFT_CURRENCY_RATES_IC.newBuilder().setBlockchain(assetsProfile.getBlockchain()).build(),
                CurrencyRatesRouteRequest.class
        ).getRatesMap();

        //total volume
        Optional<Float> volumeOptional = nftOrderDao.find(NftOrderQuery.newBuilder().assetsId(assetsId).build())
                .stream()
                .map(nftOrder -> {

                    float rate = 1;
                    if(!nftOrder.getCurrency().equals(currency)){
                        rate = currencyRates.getOrDefault(nftOrder.getCurrency(), 0f);
                    }
                    if(rate == 0){
                        return 0f;
                    }else{
                        return nftOrder.getPrice() / rate * nftOrder.getQuantity();
                    }
                })
                .reduce((a, b) -> a + b);
        if(volumeOptional.isPresent()){
            nftAssetsStatsDao.findAndModify(
                    NftAssetsStatsQuery.newBuilder().assetsId(assetsId).build(),
                    NftAssetsStatsUpdate.newBuilder()
                            .setTotalVolume(volumeOptional.get())
                            .setCurrency(currency)
                            .createAtOnInsert()
                            .build(),
                    UpdateOptions.options().upsert(true)
            );
        }

    }

    public void listingEventHandle(NftExchange.LISTING_EVENT_IC request) {

        //find the floor price;
        Long assetsId = request.getAssetsId();
        //get profile
        //get assetsProfile
        NftSearch.AssetsDTO assetsProfile = routeClient.send(
                NftSearch.ASSETS_IC.newBuilder().setAssetsId(assetsId).build(),
                AssetsRouteRequest.class
        ).getAssets();
        String currency = assetsProfile.getCurrency();

        //currencyRates;
        Map<String, Float> currencyRates = routeClient.send(
                NftSearch.NFT_CURRENCY_RATES_IC.newBuilder().setBlockchain(assetsProfile.getBlockchain()).build(),
                CurrencyRatesRouteRequest.class
        ).getRatesMap();

        Optional<Float> floor = nftListingDao
                .find(NftListingQuery.newBuilder().assetsId(assetsId).build())
                .stream()
                .filter(dto -> !Times.isExpired(dto.getExpireAt()))
                .sorted(Comparator.comparingLong(NftListing::getCreatedAt))
                .map(listing -> {
                    float rate = 1;
                    if(!listing.getCurrency().equals(currency)){
                        rate = currencyRates.getOrDefault(listing.getCurrency(), 0f);
                    }
                    if(rate == 0){
                        return 0f;
                    }else{
                        return listing.getPrice() / rate;
                    }
                })
                .filter(price -> price != 0)
                .min(Comparator.comparingDouble(price -> price));
        float floorPrice = 0;
        if(floor.isPresent()){
            floorPrice = floor.get();
        }

        //保存地板价
        nftAssetsStatsDao.findAndModify(
                NftAssetsStatsQuery.newBuilder().assetsId(assetsId).build(),
                NftAssetsStatsUpdate.newBuilder()
                        .setFloorPrice(floorPrice)
                        .setCurrency(currency)
                        .createAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true)
        );

    }
}
