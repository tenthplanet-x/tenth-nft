package com.tenth.nft.wallet;

import com.tenth.nft.wallet.entity.Wallet;
import com.tenth.nft.wallet.entity.WalletBill;
import com.tenth.nft.wallet.entity.WalletSetting;
import com.tpulse.gs.convention.dao.generate.OrmGenerator;
import org.junit.Test;

/**
 * @author shijie
 */
public class OrmGenerateTest {

    @Test
    public void generate() throws Exception{

        OrmGenerator.newBuilder()
                .dist("/Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-wallet/src/main/java")
                .entity(Wallet.class)
                .entity(WalletBill.class)
                .entity(WalletSetting.class)
                .build().start();

    }
}
