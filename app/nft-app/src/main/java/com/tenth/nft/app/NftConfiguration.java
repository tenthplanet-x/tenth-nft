package com.tenth.nft.app;

import com.tenth.nft.app.security.SimpleLockManger;
import com.tenth.nft.blockchain.NftBlockchainConfiguration;
import com.tenth.nft.convention.NftConventionConfiguration;
import com.tenth.nft.crawler.NftCrawlerConfiguration;
import com.tenth.nft.exchange.buildin.NftExchangeConfiguration;
import com.tenth.nft.NftMarketplaceConfiguration;
import com.tenth.nft.marketplace.NftPlayerConfiguration;
import com.tenth.nft.operation.NftOperationConfiguration;
import com.tenth.nft.orm.NftOrmConfiguration;
import com.tenth.nft.search.NftSearchConfiguration;
import com.tenth.nft.wallet.NftWalletConfiguration;
import com.tenth.nft.web3.Web3WalletConfiguration;
import com.tpulse.gs.GsRoutesConfiguration;
import com.tpulse.gs.config2.client.GsConfig2ClientConfiguration;
import com.tpulse.gs.config2.server.GsConfig2ServerConfiguration;
import com.tpulse.gs.convention.GsConventionConfiguration;
import com.tpulse.gs.router.client.remote.RemoteRouteClientConfiguration;
import com.tpulse.gs.router.server.RemoteRouteServerConfiguration;
import com.tpulse.gs.router.tribe.client.RouteTribeClientConfiguration;
import com.tpulse.gs.scheduer.GsSchedulerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 */
@Configuration
@Import({
        RouteTribeClientConfiguration.class,
        RemoteRouteClientConfiguration.class,
        RemoteRouteServerConfiguration.class,
        GsSchedulerConfiguration.class,
        GsConventionConfiguration.class,
        GsRoutesConfiguration.class,
        NftCrawlerConfiguration.class,
        NftConventionConfiguration.class,
        NftMarketplaceConfiguration.class,
        NftOrmConfiguration.class,
        NftSearchConfiguration.class,
        NftBlockchainConfiguration.class,
        NftExchangeConfiguration.class,
        NftOperationConfiguration.class,
        NftWalletConfiguration.class,
        GsConfig2ClientConfiguration.class,
        GsConfig2ServerConfiguration.class,
        Web3WalletConfiguration.class,
        NftPlayerConfiguration.class
})
public class NftConfiguration {

    @Value("${auth.locks: 200}")
    private int lockSize;

    @Bean
    public SimpleLockManger simpleLockManger(){
        return new SimpleLockManger(lockSize);
    }
}
