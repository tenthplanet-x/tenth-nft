package com.tenth.nft.orm;

import com.tenth.nft.orm.marketplace.entity.NftCollectionAssets;
import com.tpulse.gs.convention.dao.generate.OrmGenerator;
import org.junit.Test;

/**
 * @author shijie
 */
public class OrmGeneratorTest {

//    @Test
    public void generate() throws Exception{
        OrmGenerator.newBuilder()
                .dist("/Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-orm/src/main/java")
                .entity(NftCollectionAssets.class)
                .build().start();
    }

}
