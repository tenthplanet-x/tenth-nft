package com.tenth.nft.marketplace.stats;

import com.tenth.nft.marketplace.stats.entity.NftBlockchainVolumeStats;
import com.tpulse.gs.convention.dao.generate.OrmGenerator;
import org.junit.Test;

/**
 * @author shijie
 */
public class OrmGeneratorTest {

    @Test
    public void test() throws Exception{

        OrmGenerator.newBuilder()
                .dist("/Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-marketplace-stats/src/main/java")
                .entity(NftBlockchainVolumeStats.class)
                .build().start();

    }
}
