package com.tenth.nft.app;

import com.tenth.nft.app.security.SimpleLockManger;
import com.tenth.nft.convention.NftConventionConfiguration;
import com.tenth.nft.marketplace.NftMarketplaceConfiguration;
import com.tenth.nft.marketplace.buildin.BuildInNftMarketplaceConfiguration;
import com.tenth.nft.marketplace.common.NftMarketplaceCommonConfiguration;
import com.tenth.nft.marketplace.stats.NftMarketplaceStatsConfiguration;
import com.tenth.nft.marketplace.web3.Web3NftMarketpalceConfiguration;
import com.tenth.nft.operation.NftOperationConfiguration;
import com.tenth.nft.orm.NftOrmConfiguration;
import com.tenth.nft.wallet.BuildInWalletConfiguration;
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
        NftConventionConfiguration.class,
        NftOrmConfiguration.class,
        NftOperationConfiguration.class,
        BuildInWalletConfiguration.class,
        GsConfig2ClientConfiguration.class,
        GsConfig2ServerConfiguration.class,
        NftMarketplaceConfiguration.class,
        BuildInNftMarketplaceConfiguration.class,
        Web3NftMarketpalceConfiguration.class,
        NftMarketplaceCommonConfiguration.class,
        NftMarketplaceStatsConfiguration.class,
        Web3WalletConfiguration.class,
        BuildInWalletConfiguration.class,

})
public class NftConfiguration {

    @Value("${auth.locks: 200}")
    private int lockSize;

    @Bean
    public SimpleLockManger simpleLockManger(){
        return new SimpleLockManger(lockSize);
    }
}
