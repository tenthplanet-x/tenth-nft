package com.tenth.nft.web3;

import com.tenth.nft.web3.entity.Web3ContractApproval;
import com.tpulse.gs.convention.dao.generate.OrmGenerator;
import org.junit.Test;

/**
 * @author shijie
 */
public class OrmGenerateTest {

    @Test
    public void test() throws Exception{
        OrmGenerator.newBuilder()
                .dist("/Users/chishijie/Documents/tpulse/java/tpulse-nft/nft-web3/src/main/java")
                .entity(Web3ContractApproval.class)
//                .entity(Web3WalletEvent.class)
                .build()
                .start();
    }
}
