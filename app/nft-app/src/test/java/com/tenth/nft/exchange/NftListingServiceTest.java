package com.tenth.nft.exchange;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.exchange.common.service.NftListingService;
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
public class NftListingServiceTest {

    @Autowired
    private NftListingService nftListingService;

    @Test
    public void test(){
        Long owner = 18000l;
        Long assetsId = 45900l;
        nftListingService.refreshListingsBelongTo(owner, assetsId);
    }
}
