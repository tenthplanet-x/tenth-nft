package com.tenth.nft.marketplace.stats.service;

import com.tenth.nft.marketplace.stats.NftStatsProperties;
import com.tenth.nft.marketplace.stats.dao.*;
import com.tenth.nft.marketplace.stats.dao.expression.*;
import com.tenth.nft.marketplace.stats.entity.NftAssetsVolumeStats;
import com.tenth.nft.marketplace.stats.entity.NftAssetsTaskProgress;
import com.tenth.nft.marketplace.stats.entity.NftCollectionVolumeStats;
import com.tenth.nft.marketplace.stats.entity.NftBlockchainVolumeStats;
import com.tenth.nft.marketplace.stats.utils.DateSplitter;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import com.tpulse.gs.convention.dao.defination.UpdateOptions;
import com.tpulse.gs.convention.utils.DateTimes;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shijie
 */
@Service
public class NftExchangeStatsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NftExchangeStatsService.class);

    public static final Long TASK_PROGRESS_ID = 1L;

    @Autowired
    private NftAssetsTaskProgressDao nftAssetsTaskProgressDao;
    @Autowired
    private NftAssetsExchangeLogDao nftAssetsExchangeLogDao;
    @Autowired
    private NftAssetsVolumeStatsDao nftAssetsVolumeStatsDao;
    @Autowired
    private NftStatsProperties nftStatsProperties;
    @Autowired
    private NftCollectionVolumeStatsDao nftCollectionVolumeStatsDao;
    @Autowired
    private NftBlockchainVolumeStatsDao nftBlockchainVolumeStatsDao;

    public void doStats(){

        DateTime now = DateTime.now();

        long start = DateTimes.getRange(now.getMillis(), DateTimes.TimeDimension.DAY).getStart();
        long end = now.getMillis();
        NftAssetsTaskProgress progress = nftAssetsTaskProgressDao.findOne(NftAssetsTaskProgressQuery.newBuilder().id(TASK_PROGRESS_ID).build());
        if(null != progress){
            start = progress.getLastTime() + 1;
        }

        if(end > start){

            List<DateSplitter.DailyRange> dailyRanges = DateSplitter.dailySplit(start, end);
            for(DateSplitter.DailyRange dailyRange: dailyRanges){
                String startDateTime = new DateTime(dailyRange.start()).toString("yyyy-MM-dd HH:mm:ss");
                String endDatetime = new DateTime(dailyRange.end()).toString("yyyy-MM-dd HH:mm:ss");
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("stats start, start: {}, end: {}", startDateTime, endDatetime);
                }
                long startedAt = System.currentTimeMillis();
                try{
                    //Stats group by assets
                    doAssetsStats(dailyRange);
                    //Stats group by collection
                    doCollectionStats(dailyRange);
                    //Stats global
                    doBlockchainStats(dailyRange);
                    //Update last stats time
                    updateTaskProgress(dailyRange.end());
                    if(LOGGER.isDebugEnabled()){
                        long cost = System.currentTimeMillis() - startedAt;
                        LOGGER.debug("stats complete, start: {}, end: {}, cost: {}ms", startDateTime, endDatetime, cost);
                    }
                }catch (Exception e){
                    LOGGER.error(String.format("stats fail, start: %s, end: %s", startDateTime, endDatetime), e);
                }
            }

        }


    }


    private void doAssetsStats(DateSplitter.DailyRange dailyRange) {

        long start = dailyRange.start();
        long end = dailyRange.end();

        List<NftAssetsVolumeStats> nftAssetsStats = nftAssetsExchangeLogDao.doStatsGroupByAssetsId(start, end);
        for(NftAssetsVolumeStats stats: nftAssetsStats){
            //total volume
            DateTimes.TimeDimension[] timeDimensions = new DateTimes.TimeDimension[]{DateTimes.TimeDimension.TOTAL, DateTimes.TimeDimension.DAY};
            for(DateTimes.TimeDimension timeDimension: timeDimensions){

                Long timestamp = (timeDimension == DateTimes.TimeDimension.TOTAL)? 0l: DateTimes.adjustFormatTime(start, timeDimension);
                nftAssetsVolumeStatsDao.findAndModify(
                        NftAssetsVolumeStatsQuery.newBuilder()
                                .timeDimension(timeDimension.name())
                                .timeStamp(timestamp)
                                .blockchain(stats.getBlockchain())
                                .collectionId(stats.getCollectionId())
                                .assetsId(stats.getAssetsId())
                                .build(),
                        NftAssetsVolumeStatsUpdate.newBuilder()
                                .exchangesInc(stats.getExchanges())
                                .volumeInc(stats.getVolume())
                                .createdAtOnInsert()
                                .lastExchangedAt(stats.getLastExchangedAt())
                                .build(),
                        UpdateOptions.options().upsert(true)
                );
            }
        }
    }

    private void doCollectionStats(DateSplitter.DailyRange dailyRange) {

        long start = dailyRange.start();
        long end = dailyRange.end();

        List<NftCollectionVolumeStats> nftAssetsStats = nftAssetsExchangeLogDao.doStatsGroupByCollection(start, end);
        for(NftCollectionVolumeStats stats: nftAssetsStats){
            //total volume
            DateTimes.TimeDimension[] timeDimensions = new DateTimes.TimeDimension[]{DateTimes.TimeDimension.TOTAL, DateTimes.TimeDimension.DAY};
            for(DateTimes.TimeDimension timeDimension: timeDimensions){

                Long timestamp = (timeDimension == DateTimes.TimeDimension.TOTAL)? 0l: DateTimes.adjustFormatTime(start, timeDimension);;
                nftCollectionVolumeStatsDao.findAndModify(
                        NftCollectionVolumeStatsQuery.newBuilder()
                                .timeDimension(timeDimension.name())
                                .timeStamp(timestamp)
                                .blockchain(stats.getBlockchain())
                                .collectionId(stats.getCollectionId())
                                .build(),
                        NftCollectionVolumeStatsUpdate.newBuilder()
                                .exchangesInc(stats.getExchanges())
                                .volumeInc(stats.getVolume())
                                .createdAtOnInsert()
                                .lastExchangedAt(stats.getLastExchangedAt())
                                .build(),
                        UpdateOptions.options().upsert(true)
                );
            }
        }
    }

    private void doBlockchainStats(DateSplitter.DailyRange dailyRange) {

        long start = dailyRange.start();
        long end = dailyRange.end();

        List<NftBlockchainVolumeStats> nftAssetsStats = nftAssetsExchangeLogDao.doBlockchainStats(start, end);
        for(NftBlockchainVolumeStats stats: nftAssetsStats){
            //total volume
            DateTimes.TimeDimension[] timeDimensions = new DateTimes.TimeDimension[]{DateTimes.TimeDimension.TOTAL, DateTimes.TimeDimension.DAY};
            for(DateTimes.TimeDimension timeDimension: timeDimensions){

                Long timestamp = (timeDimension == DateTimes.TimeDimension.TOTAL)? 0l: DateTimes.adjustFormatTime(start, timeDimension);;
                nftBlockchainVolumeStatsDao.findAndModify(
                        NftBlockchainVolumeStatsQuery.newBuilder()
                                .timeDimension(timeDimension.name())
                                .timeStamp(timestamp)
                                .blockchain(stats.getBlockchain())
                                .build(),
                        NftBlockchainVolumeStatsUpdate.newBuilder()
                                .exchangesInc(stats.getExchanges())
                                .volumeInc(stats.getVolume())
                                .createdAtOnInsert()
                                .build(),
                        UpdateOptions.options().upsert(true)
                );
            }
        }

    }

    private void updateTaskProgress(long end) {
        nftAssetsTaskProgressDao.findAndModify(
                NftAssetsTaskProgressQuery.newBuilder()
                        .id(TASK_PROGRESS_ID)
                        .build(),
                NftAssetsTaskProgressUpdate.newBuilder()
                        .setLastTime(end)
                        .createdAtOnInsert()
                        .build(),
                UpdateOptions.options().upsert(true)
        );
    }


}
