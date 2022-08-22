package com.tenth.nft.wallet;

import com.tenth.nft.app.NftApplicationTest;
import com.tenth.nft.convention.routes.wallet.WalletRechargeRouteRequest;
import com.tenth.nft.convention.wallet.*;
import com.tenth.nft.exchange.wallet.BuildInWalletProvider;
import com.tenth.nft.protobuf.NftWallet;
import com.tpulse.gs.router.client.RouteClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * @author shijie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NftApplicationTest.class)
public class BuildinWalletProviderTest {

    @Autowired
    private BuildInWalletProvider buildInWalletProvider;
    @Value("${wallet.rsa.public-key}")
    private String publicKey;
    @Autowired
    private RouteClient routeClient;

    @Test
    public void createToken(){

        WalletOrderBizContent content = WalletOrderBizContent.newBuilder()
                .activityCfgId(WalletOrderType.NftExpense.getActivityCfgId())
                .productCode(WalletProductCode.NFT.name())
                .productId("1")
                .outOrderId("1")
//                .merchantType(WalletMerchantType.PERSONAL.name())
//                .merchantId(1l)
                .currency("TPC")
                .value(BigDecimal.valueOf(1000).toString())
                .expiredAt(System.currentTimeMillis() + 86400000)
                .build();
        String token = buildInWalletProvider.createToken(content);
        System.out.println(token);

        WalletToken walletToken = WalletToken.decode(token);
        Assert.isTrue(walletToken.verify(publicKey), "not true");
    }

    @Test
    public void recharge(){

        WalletOrderBizContent content = WalletOrderBizContent.newBuilder()
                .activityCfgId(WalletOrderType.FundsIncome.getActivityCfgId())
                .productCode(WalletProductCode.WALLET_RECHARGE.name())
                .productId("1")
                .outOrderId("2")
//                .merchantType(WalletMerchantType.PERSONAL.name())
//                .merchantId(1l)
                .currency("TPC")
                .value(BigDecimal.valueOf(1000).toString())
                .expiredAt(System.currentTimeMillis() + 86400000)
                .build();
        String token = buildInWalletProvider.createToken(content);

        routeClient.send(
                NftWallet.RECHARGE_IC.newBuilder()
                        .setUid(18000)
                        .setToken(token)
                        .build(),
                WalletRechargeRouteRequest.class
        );
    }

}
