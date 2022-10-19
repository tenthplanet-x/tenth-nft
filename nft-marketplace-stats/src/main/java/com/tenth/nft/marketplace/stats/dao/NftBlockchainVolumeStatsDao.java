package com.tenth.nft.marketplace.stats.dao;

import com.tenth.nft.marketplace.stats.dao.expression.NftBlockchainVolumeStatsQuery;
import com.tenth.nft.marketplace.stats.entity.NftBlockchainVolumeStats;
import com.tpulse.gs.convention.dao.SimpleDao;
import com.tpulse.gs.convention.utils.DateTimes;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gs-orm-generator
 * @createdAt 2022/09/28 14:13
 */
@Component
public class NftBlockchainVolumeStatsDao extends SimpleDao<NftBlockchainVolumeStats> {

    public static NftBlockchainVolumeStats EMPTY;
    static {
        EMPTY = new NftBlockchainVolumeStats();
        EMPTY.setVolume(BigDecimal.ZERO);
        EMPTY.setExchanges(0);
    }


    public NftBlockchainVolumeStatsDao() {
        super(NftBlockchainVolumeStats.class);
    }

    public NftBlockchainVolumeStats findTotal() {
        NftBlockchainVolumeStats stats = findOne(NftBlockchainVolumeStatsQuery.newBuilder().timeDimension(DateTimes.TimeDimension.TOTAL.name()).build());
        if(null == stats){
            return EMPTY;
        }
        return stats;
    }

    public Map<String, NftBlockchainVolumeStats> findTotalByBlockchain() {

        return find(NftBlockchainVolumeStatsQuery.newBuilder().timeDimension(DateTimes.TimeDimension.TOTAL.name()).build())
                .stream()
                .collect(Collectors.toMap(NftBlockchainVolumeStats::getBlockchain, Function.identity()))
        ;

    }

    public Map<String, NftBlockchainVolumeStats> findDateByBlockchain(Long date) {
        return find(NftBlockchainVolumeStatsQuery.newBuilder().timeDimension(DateTimes.TimeDimension.DAY.name()).timeStamp(date).build())
                .stream()
                .collect(Collectors.toMap(NftBlockchainVolumeStats::getBlockchain, Function.identity()))
                ;
    }
}