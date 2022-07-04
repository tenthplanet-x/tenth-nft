package com.tenth.nft.crawler;

import com.tenth.nft.orm.entity.ExternalNftCollection;
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
                .entity(ExternalNftCollection.class)
                .enableMvc(true)
                .build()
                .start();
    }
}
