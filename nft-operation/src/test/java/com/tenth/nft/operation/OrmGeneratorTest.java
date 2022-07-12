package com.tenth.nft.operation;

import com.tenth.nft.orm.marketplace.entity.CurrencyRate;
import com.tpulse.gs.convention.dao.generate.OrmGenerator;

/**
 * @author shijie
 */
public class OrmGeneratorTest {

//    @Test
    public void test() throws Exception{

        OrmGenerator.newBuilder()
                .dist("/Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-operation/src/main/java")
                .enableMvc(true)
                .entity(CurrencyRate.class)
                .build()
                .start();
        ;

    }
}
