package com.tenth.nft.crawler;

import com.tenth.nft.orm.entity.NftCollection;
import com.tpulse.gs.convention.dao.generate.OrmGenerator;
import org.junit.Test;

/**
 * @author shijie
 */
public class OrmGenerators {

    @Test
    public void generate() throws Exception{
        OrmGenerator.newBuilder()
                .dist("/Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-crawler/src/main/java")
                .entity(NftCollection.class)
                .enableMvc(true)
                .build()
                .start();
    }
}
