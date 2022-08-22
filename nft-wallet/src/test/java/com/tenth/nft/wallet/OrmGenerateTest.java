package com.tenth.nft.wallet;

import com.tpulse.gs.convention.dao.generate.OrmGenerator;

/**
 * @author shijie
 */
public class OrmGenerateTest {

//    @Test
    public void generate() throws Exception{

        OrmGenerator.newBuilder()
                .dist("/Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-wallet/src/main/java")
                .entity(WalletBillEvent.class)
//                .entity(Wallet.class)
//                .entity(WalletBill.class)
//                .entity(WalletSetting.class)
                .build().start();

    }
}
