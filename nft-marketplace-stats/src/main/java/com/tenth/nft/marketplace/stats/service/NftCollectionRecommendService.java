package com.tenth.nft.marketplace.stats.service;

import com.ruixi.tpulse.convention.protobuf.app.AppChat;
import com.tenth.nft.convention.Web3Properties;
import com.tenth.nft.convention.routes.marketplace.buildin.BuildInAssetsDetailBatchRouteRequest;
import com.tenth.nft.convention.routes.marketplace.buildin.BuildInCollectionDetailRouteRequest;
import com.tenth.nft.convention.routes.marketplace.web3.Web3AssetsDetailBatchRouteRequest;
import com.tenth.nft.convention.routes.marketplace.web3.Web3CollectionDetailRouteRequest;
import com.tenth.nft.convention.templates.BlockchainTemplate;
import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.convention.wallet.utils.BigNumberUtils;
import com.tenth.nft.marketplace.common.entity.NftAssetsType;
import com.tenth.nft.marketplace.stats.dao.*;
import com.tenth.nft.marketplace.stats.dao.expression.NftAssetsVolumeStatsQuery;
import com.tenth.nft.marketplace.stats.dao.expression.NftCollectionRecommendQuery;
import com.tenth.nft.marketplace.stats.dao.expression.NftCollectionRecommendUpdate;
import com.tenth.nft.marketplace.stats.dao.expression.NftCollectionVolumeStatsQuery;
import com.tenth.nft.marketplace.stats.dto.NftCollectionRecommendDTO;
import com.tenth.nft.marketplace.stats.entity.*;
import com.tenth.nft.marketplace.stats.vo.CollectionRecommentListSearchRequest;
import com.tenth.nft.protobuf.NftMarketplace;
import com.tpulse.gs.convention.dao.SimpleQuerySorts;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.dao.dto.Page;
import com.tpulse.gs.convention.utils.DateTimes;
import com.tpulse.gs.router.client.RouteClient;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author shijie
 */
@Service
public class NftCollectionRecommendService {

    @Autowired
    private NftCollectionRecommendDao nftCollectionRecommendDao;
    @Autowired
    private NftCollectionRecommendVersionDao nftCollectionRecommendVersionDao;
    @Autowired
    private NftCollectionVolumeStatsDao nftCollectionVolumeStatsDao;
    @Autowired
    private NftBlockchainVolumeStatsDao nftBlockchainVolumeStatsDao;
    @Autowired
    private NftAssetsVolumeStatsDao nftAssetsVolumeStatsDao;
    @Autowired
    private RouteClient routeClient;
    @Autowired
    private Web3Properties web3Properties;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    public Page<NftCollectionRecommendDTO> recommendList(CollectionRecommentListSearchRequest request) {

        NftCollectionRecommendVersion version = nftCollectionRecommendVersionDao.findVersion();
        if(null != version){
            return nftCollectionRecommendDao.findPage(
                    NftCollectionRecommendQuery.newBuilder()
                            .categoryId(request.getCategoryId())
                            .setPage(request.getPage())
                            .setPageSize(request.getPageSize())
                            .setSortField("score")
                            .setReverse(true)
                            .build(),
                    NftCollectionRecommendDTO.class
            );
        }

        return new Page<>();
    }

    public void doStats(){

        NftCollectionRecommendVersion version = nftCollectionRecommendVersionDao.findVersion();

        String newVersion = DateTime.now().toString("yyyyMMddHHmmss");
        Long date = DateTimes.adjustFormatTime(System.currentTimeMillis(), DateTimes.TimeDimension.DAY);

        //total Volume
        {

            int page = 1;
            int pageSize = 100;
            boolean hasMore = true;
            do{
                Page<NftCollectionVolumeStats> dataPage = nftCollectionVolumeStatsDao.findPage(NftCollectionVolumeStatsQuery.newBuilder()
                        .timeDimension(DateTimes.TimeDimension.TOTAL.name())
                        .timeStamp(0l)
                        .setPage(page++)
                        .setPageSize(pageSize)
                        .build()
                );

                if(dataPage.getData().isEmpty()){
                    hasMore = false;
                    continue;
                }

                for(NftCollectionVolumeStats stats: dataPage.getData()){
                    nftCollectionRecommendDao.findAndModify(
                            NftCollectionRecommendQuery.newBuilder()
                                    .version(newVersion)
                                    .blockchain(stats.getBlockchain())
                                    .collectionId(stats.getCollectionId())
                                    .build(),
                            NftCollectionRecommendUpdate.newBuilder()
                                    .totalVolume(stats.getVolume())
                                    .lastExchangedAt(stats.getLastExchangedAt())
                                    .build(),
                            UpdateOptions.options().upsert(true)
                    );
                }

            }while (hasMore);
        }

        //date Volume
        {

            int page = 1;
            int pageSize = 100;
            boolean hasMore = true;
            Long timestamp = DateTimes.adjustFormatTime(System.currentTimeMillis(), DateTimes.TimeDimension.DAY);
            do{
                Page<NftCollectionVolumeStats> dataPage = nftCollectionVolumeStatsDao.findPage(NftCollectionVolumeStatsQuery.newBuilder()
                        .timeDimension(DateTimes.TimeDimension.DAY.name())
                        .timeStamp(timestamp)
                        .setPage(page++)
                        .setPageSize(pageSize)
                        .build()
                );

                if(dataPage.getData().isEmpty()){
                    hasMore = false;
                    continue;
                }

                for(NftCollectionVolumeStats stats: dataPage.getData()){

                    nftCollectionRecommendDao.findAndModify(
                            NftCollectionRecommendQuery.newBuilder()
                                    .version(newVersion)
                                    .collectionId(stats.getCollectionId())
                                    .build(),
                            NftCollectionRecommendUpdate.newBuilder()
                                    .dateVolume(stats.getVolume())
                                    .build(),
                            UpdateOptions.options().upsert(true)
                    );
                }

            }while (hasMore);

        }

        //Calculate score and fill with profile
        {
            Map<String, NftBlockchainVolumeStats> blockchainGlobalVolumeStatsMap = nftBlockchainVolumeStatsDao.findTotalByBlockchain();
            Map<String, NftBlockchainVolumeStats> blockchainDateVolumeStatsMap = nftBlockchainVolumeStatsDao.findDateByBlockchain(date);

            int page = 1;
            int pageSize = 100;
            boolean hasMore = true;
            do {

                Page<NftCollectionRecommend> dataPage = nftCollectionRecommendDao.findPage(NftCollectionRecommendQuery.newBuilder()
                        .version(newVersion)
                        .setPage(page++)
                        .setPageSize(pageSize)
                        .build());

                if(dataPage.getData().isEmpty()){
                    hasMore = false;
                    continue;
                }
                for(NftCollectionRecommend recommend: dataPage.getData()){

                    NftBlockchainVolumeStats blockchainVolumeStats = blockchainGlobalVolumeStatsMap.get(recommend.getBlockchain());
                    NftBlockchainVolumeStats blockchainDateVolumeStats = blockchainDateVolumeStatsMap.get(recommend.getBlockchain());
                    //totalVolumeRate
                    BigDecimal totalVolumeRate = BigDecimal.ZERO;
                    if(null != blockchainVolumeStats){
                        totalVolumeRate = BigNumberUtils.divide(recommend.getTotalVolume(), blockchainVolumeStats.getVolume());
                    }
                    totalVolumeRate.setScale(8);
                    //dateVolumeRate
                    BigDecimal dateVolumeRate = BigDecimal.ZERO;
                    if(null != blockchainDateVolumeStats){
                        dateVolumeRate = BigNumberUtils.divide(recommend.getDateVolume(), blockchainDateVolumeStats.getVolume());
                    }
                    dateVolumeRate.setScale(8);
                    //dateRate
                    double dayBetween = DateTimes.duration(date, recommend.getLastExchangedAt(), DateTimes.TimeDimension.DAY);
                    double dateRate = 1 - (dayBetween > 0d? (1 / dayBetween): 0);
                    double score = 0.25 * dateRate + 0.25 * dateVolumeRate.doubleValue() + 0.5 * totalVolumeRate.doubleValue();

                    //get profiles
                    NftMarketplace.CollectionDTO collectionProfile = getCollectionProfile(recommend.getBlockchain(), recommend.getCollectionId());
                    String currency = i18nGsTemplates.get(NftTemplateTypes.wallet_currency, WalletCurrencyTemplate.class).findMainCurrency(recommend.getBlockchain()).getCode();

                    //get recommendAssets
                    List<NftCollectionRecommendAssets> assetsProfiles = getAssetsProfile(recommend.getBlockchain(), recommend.getCollectionId());

                    nftCollectionRecommendDao.update(
                            NftCollectionRecommendQuery.newBuilder().collectionId(recommend.getCollectionId()).build(),
                            NftCollectionRecommendUpdate.newBuilder()
                                    .setCategoryId(collectionProfile.getCategory())
                                    .setLogoImage(collectionProfile.getLogoImage())
                                    .setFeaturedImage(collectionProfile.getFeaturedImage())
                                    .setItems(collectionProfile.getItems())
                                    .setName(collectionProfile.getName())
                                    .totalVolumeRate(totalVolumeRate.doubleValue())
                                    .dateVolumeRate(dateVolumeRate.doubleValue())
                                    .dateRate(dateRate)
                                    .score(score)
                                    .setRecommendAssets(assetsProfiles)
                                    .setCurrency(currency)
                                    .build()
                    );

                }

            }while (hasMore);
        }

        nftCollectionRecommendVersionDao.updateVersion(newVersion);
        if(null != version){
            nftCollectionRecommendDao.clearAllByVersion(version.getVersion());
        }

    }

    private NftMarketplace.CollectionDTO getCollectionProfile(String blockchain, Long collectionId) {

        NftMarketplace.CollectionDTO collectionProfile = null;

        if(web3Properties.getBlockchain().equals(blockchain)){
            collectionProfile = routeClient.send(
                    NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                            .setId(collectionId)
                            .build(),
                    Web3CollectionDetailRouteRequest.class
            ).getCollection();
        }else{
            collectionProfile = routeClient.send(
                    NftMarketplace.COLLECTION_DETAIL_IC.newBuilder()
                            .setId(collectionId)
                            .build(),
                    BuildInCollectionDetailRouteRequest.class
            ).getCollection();
        }
        return collectionProfile;
    }

    private List<NftCollectionRecommendAssets> getAssetsProfile(String blockchain, Long collectionId) {

        List<NftAssetsVolumeStats> assetsVolumeStats = nftAssetsVolumeStatsDao.find(
                NftAssetsVolumeStatsQuery.newBuilder()
                        .timeDimension(DateTimes.TimeDimension.TOTAL.name())
                        .timeStamp(0l)
                        .collectionId(collectionId)
                        .setLimit(3)
                        .setSorts(SimpleQuerySorts.newBuilder()
                                .sort("volume", true)
                                .sort("lastExchangedAt", true)
                                .build()
                        )
                        .build()
        );
        List<Long> assetsIds = assetsVolumeStats.stream().map(stats -> stats.getAssetsId()).collect(Collectors.toList());
        final Map<Long, NftMarketplace.AssetsDTO> assetsProfileMapping = new HashMap<>();
        if(web3Properties.getBlockchain().equals(blockchain)){
            //web3
            assetsProfileMapping.putAll(
                    routeClient.send(
                            NftMarketplace.ASSETS_DETAIL_BATCH_IC.newBuilder()
                                    .addAllAssetsId(assetsIds)
                                    .build(),
                            Web3AssetsDetailBatchRouteRequest.class
                    ).getAssetsList().stream().collect(Collectors.toMap(NftMarketplace.AssetsDTO::getId, Function.identity()))
            );
        }else{
            //buildIn
            assetsProfileMapping.putAll(
                    routeClient.send(
                            NftMarketplace.ASSETS_DETAIL_BATCH_IC.newBuilder()
                                    .addAllAssetsId(assetsIds)
                                    .build(),
                            BuildInAssetsDetailBatchRouteRequest.class
                    ).getAssetsList().stream().collect(Collectors.toMap(NftMarketplace.AssetsDTO::getId, Function.identity()))
            );
        }

        return assetsVolumeStats.stream().map(stats -> {
            NftCollectionRecommendAssets assets = new NftCollectionRecommendAssets();
            assets.setId(stats.getAssetsId());
            NftMarketplace.AssetsDTO assetsProfile = assetsProfileMapping.get(stats.getAssetsId());
            assets.setPreviewUrl(assetsProfile.getPreviewUrl());
            assets.setType(NftAssetsType.valueOf(assetsProfile.getType()));
            return assets;
        }).collect(Collectors.toList());

    }

}
