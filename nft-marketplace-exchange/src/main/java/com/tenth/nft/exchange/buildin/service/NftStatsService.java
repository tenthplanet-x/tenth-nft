package com.tenth.nft.exchange.buildin.service;

import com.tenth.nft.convention.routes.CollectionRebuildRouteRequest;
import com.tenth.nft.convention.routes.marketplace.AssetsDetailRouteRequest;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
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
import com.tenth.nft.protobuf.NftSearch;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.router.client.RouteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private I18nGsTemplates i18nGsTemplates;

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
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        String currency = walletCurrencyTemplate.findMainCurrency(blockchain).getCode();

        //total volume
        Optional<BigDecimal> volumeOptional = nftOrderDao.find(NftOrderQuery.newBuilder().assetsId(assetsId).build())
                .stream()
                .filter(nftOrder -> nftOrder.getCurrency().equals(currency))
                .map(nftOrder -> new BigDecimal(nftOrder.getPrice()))
                .reduce((a, b) -> a.add(b));
        if(volumeOptional.isPresent()){
            nftAssetsStatsDao.findAndModify(
                    NftAssetsStatsQuery.newBuilder().assetsId(assetsId).build(),
                    NftAssetsStatsUpdate.newBuilder()
                            .setTotalVolume(volumeOptional.get().toString())
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
        WalletCurrencyTemplate walletCurrencyTemplate = i18nGsTemplates.get(NftTemplateTypes.wallet_currency);
        String currency = walletCurrencyTemplate.findMainCurrency(blockchain).getCode();

        //currencyRates;
        Map<String, BigDecimal> currencyRates = walletCurrencyTemplate.findByBlockchain(blockchain)
                .stream()
                .collect(Collectors.toMap(
                        cfgs -> cfgs.getCode(),
                        cfgs -> new BigDecimal(cfgs.getExchangeRate())
                ));

        Optional<BigDecimal> floor = nftListingDao
                .find(NftListingQuery.newBuilder().assetsId(assetsId).build())
                .stream()
                .filter(dto -> !Times.isExpired(dto.getExpireAt()))
                .filter(dto -> dto.getCurrency().equals(currency))
                .sorted(Comparator.comparingLong(NftListing::getCreatedAt))
                .map(listing -> {
                    BigDecimal rate = BigDecimal.ONE;
                    if(!listing.getCurrency().equals(currency)){
                        rate = currencyRates.getOrDefault(listing.getCurrency(), BigDecimal.ZERO);
                    }
                    if(rate.compareTo(BigDecimal.ZERO) == 0){
                        return new BigDecimal(0);
                    }else{
                        return new BigDecimal(listing.getPrice()).divide(rate);
                    }
                })
                .filter(price -> price.compareTo(BigDecimal.ZERO) > 0)
                .min(Comparator.comparing(price -> price));
        BigDecimal floorPrice = new BigDecimal(0);
        if(floor.isPresent()){
            floorPrice = floor.get();
        }
        //保存地板价
        nftAssetsStatsDao.findAndModify(
                NftAssetsStatsQuery.newBuilder().assetsId(assetsId).build(),
                NftAssetsStatsUpdate.newBuilder()
                        .setFloorPrice(floorPrice.toString())
                        .setCurrency(currency)
                        .createAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true)
        );

    }
}
