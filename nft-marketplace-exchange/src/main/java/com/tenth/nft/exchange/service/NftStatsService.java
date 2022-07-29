package com.tenth.nft.exchange.service;

import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.routes.operation.BlockchainRouteRequest;
import com.tenth.nft.convention.routes.search.CurrencyRatesRouteRequest;
import com.tenth.nft.convention.utils.Times;
import com.tenth.nft.orm.marketplace.dao.NftAssetsStatsDao;
import com.tenth.nft.orm.marketplace.dao.NftListingDao;
import com.tenth.nft.orm.marketplace.dao.NftOrderDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsStatsQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftAssetsStatsUpdate;
import com.tenth.nft.orm.marketplace.dao.expression.NftListingQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftOrderQuery;
import com.tenth.nft.orm.marketplace.entity.NftListing;
import com.tenth.nft.protobuf.NftExchange;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tenth.nft.protobuf.NftOperation;
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.router.client.RouteClient;
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
        NftMarketplace.AssetsDTO assetsDTO = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(assetsId)
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets();
        String blockchain = assetsDTO.getBlockchain();
        String currency = routeClient.send(
                NftOperation.NFT_BLOCKCHAIN_IC.newBuilder()
                        .setBlockchain(blockchain)
                        .build(),
                BlockchainRouteRequest.class
        ).getBlockchain().getMainCurrency();

        //currencyRates;
        Map<String, Float> currencyRates = routeClient.send(
                NftSearch.NFT_CURRENCY_RATES_IC.newBuilder().setBlockchain(blockchain).build(),
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


        //rebuild cache
        routeClient.send(
                NftSearch.NFT_COLLECTION_REBUILD_IC.newBuilder()
                        .setCollectionId(assetsDTO.getCollectionId())
                        .build(),
                CollectionRebuildRouteRequest.class
        );

    }

    public void listingEventHandle(NftExchange.LISTING_EVENT_IC request) {

        //find the floor price;
        Long assetsId = request.getAssetsId();
        //get profile
        //get assetsProfile
        String blockchain = routeClient.send(
                NftMarketplace.ASSETS_DETAIL_IC.newBuilder()
                        .setId(assetsId)
                        .build(),
                AssetsDetailRouteRequest.class
        ).getAssets().getBlockchain();
        String currency = routeClient.send(
                NftOperation.NFT_BLOCKCHAIN_IC.newBuilder()
                        .setBlockchain(blockchain)
                        .build(),
                BlockchainRouteRequest.class
        ).getBlockchain().getMainCurrency();

        //currencyRates;
        Map<String, Float> currencyRates = routeClient.send(
                NftSearch.NFT_CURRENCY_RATES_IC.newBuilder().setBlockchain(blockchain).build(),
                CurrencyRatesRouteRequest.class
        ).getRatesMap();

        Optional<Float> floor = nftListingDao
                .find(NftListingQuery.newBuilder().assetsId(assetsId).build())
                .stream()
                .filter(dto -> !Times.isExpired(dto.getExpireAt()))
                .filter(dto -> dto.getCurrency().equals(currency))
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
