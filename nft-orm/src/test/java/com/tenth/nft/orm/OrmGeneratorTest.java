package com.tenth.nft.orm;

import com.tenth.nft.orm.marketplace.entity.*;
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
                .entity(NftContract.class)
                .entity(NftListing.class)
                .entity(NftMint.class)
                .entity(NftOrder.class)
                .entity(NftBelong.class)
                .entity(NftActivity.class)
                .build().start();
    }
}
