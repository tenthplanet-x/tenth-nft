package com.tenth.nft.marketplace.stats;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.marketplace.stats.service.NftAssetsExchangeLogService;
import com.tenth.nft.marketplace.stats.service.NftCollectionRecommendService;
import com.tenth.nft.marketplace.stats.service.NftExchangeStatsService;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class NftExchangeStatsServiceTest {

    @Autowired
    private NftExchangeStatsService nftExchangeStatsService;
    @Autowired
    private NftAssetsExchangeLogService nftAssetsExchangeLogService;
    @Autowired
    private NftCollectionRecommendService nftCollectionRecommendService;

    @Test
    public void insert(){

        NftMarketplaceStats.ExchangeLog log = NftMarketplaceStats.ExchangeLog.newBuilder()
                .setBlockchain("T Planet")
                .setCollectionId(44800l)
                .setAssetsId(49000l)
                .setQuantity(1l)
                .setExchangedAt(System.currentTimeMillis())
                .setPrice(new BigDecimal("0.00001").toString())
                .build();

        NftMarketplaceStats.EXCHANGE_LOG_IC request = NftMarketplaceStats.EXCHANGE_LOG_IC.newBuilder()
                .setLog(log)
                .build();
        nftAssetsExchangeLogService.insert(request);

    }

    @Test
    public void doStat(){
        nftExchangeStatsService.doStats();
    }

    @Test
    public void doRecStats(){
        nftCollectionRecommendService.doStats();
    }


}
