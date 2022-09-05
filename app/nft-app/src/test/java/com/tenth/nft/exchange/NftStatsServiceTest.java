package com.tenth.nft.exchange;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.exchange.common.service.NftStatsService;
import com.tenth.nft.protobuf.NftExchange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class NftStatsServiceTest {

    @Autowired
    private NftStatsService nftStatsService;

    @Test
    public void exchangeEventHandle(){

        nftStatsService.exchangeEventHandle(NftExchange.EXCHANGE_EVENT_IC.newBuilder()
                .setAssetsId(45900)
                .build());
    }

    @Test
    public void listingEventHandle(){
        nftStatsService.listingEventHandle(NftExchange.LISTING_EVENT_IC.newBuilder()
                .setAssetsId(45900)
                .build());
    }


}
