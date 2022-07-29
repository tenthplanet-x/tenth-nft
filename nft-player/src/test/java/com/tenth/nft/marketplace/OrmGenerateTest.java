package com.tenth.nft.marketplace;

import com.tenth.nft.marketplace.entity.PlayerAssets;
import com.tenth.nft.marketplace.entity.PlayerCollection;
import com.tpulse.gs.convention.dao.generate.OrmGenerator;
import org.junit.Test;

/**
 * @author shijie
 */
public class OrmGenerateTest {


    @Test
    public void generate() throws Exception{

        OrmGenerator.newBuilder()
                .dist("/Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-player/src/main/java")
                .entity(PlayerAssets.class)
                .entity(PlayerCollection.class)
                .build()
                .start();

    }

}
