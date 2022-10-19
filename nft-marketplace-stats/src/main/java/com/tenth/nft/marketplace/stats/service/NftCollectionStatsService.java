package com.tenth.nft.marketplace.stats.service;

import com.tenth.nft.convention.templates.I18nGsTemplates;
import com.tenth.nft.convention.templates.NftTemplateTypes;
import com.tenth.nft.convention.templates.WalletCurrencyTemplate;
import com.tenth.nft.marketplace.stats.dao.NftCollectionVolumeStatsDao;
import com.tenth.nft.marketplace.stats.dao.expression.NftCollectionVolumeStatsQuery;
import com.tenth.nft.marketplace.stats.entity.NftCollectionVolumeStats;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import com.tpulse.gs.convention.utils.DateTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shijie
 */
@Service
public class NftCollectionStatsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NftCollectionStatsService.class);

    @Autowired
    private NftCollectionVolumeStatsDao nftCollectionVolumeStatsDao;
    @Autowired
    private I18nGsTemplates i18nGsTemplates;

    public NftMarketplaceStats.COLLECTION_VOLUME_STATS_IS volumeStats(NftMarketplaceStats.COLLECTION_VOLUME_STATS_IC request) {

        NftCollectionVolumeStats stats = nftCollectionVolumeStatsDao.findOne(
                NftCollectionVolumeStatsQuery.newBuilder().timeStamp(0l).timeDimension(DateTimes.TimeDimension.TOTAL.name()).build()
        );

        NftMarketplaceStats.CollectionVolumeStatsDTO.Builder statsBuilder = NftMarketplaceStats.CollectionVolumeStatsDTO.newBuilder();
        if(null != stats){

            String currency = i18nGsTemplates.get(NftTemplateTypes.wallet_currency, WalletCurrencyTemplate.class).findMainCurrency(stats.getBlockchain()).getCode();

            statsBuilder.setCollectionId(request.getCollectionId());
            statsBuilder.setTotalVolume(stats.getVolume().toString());
            statsBuilder.setCurrency(currency);
            return NftMarketplaceStats.COLLECTION_VOLUME_STATS_IS.newBuilder()
                    .setStats(statsBuilder.build())
                    .build();
        }

        return NftMarketplaceStats.COLLECTION_VOLUME_STATS_IS.newBuilder()
                .build();



    }
}
