package com.tenth.nft.app;

import com.tenth.nft.app.security.SimpleLockManger;
import com.tenth.nft.convention.NftConventionConfiguration;
import com.tenth.nft.crawler.NftCrawlerConfiguration;
import com.tenth.nft.marketplace.NftMarketplaceConfiguration;
import com.tenth.nft.orm.NftOrmConfiguration;
import com.tenth.nft.search.NftSearchConfiguration;
import com.tpulse.gs.GsRoutesConfiguration;
import com.tpulse.gs.convention.GsConventionConfiguration;
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
        GsSchedulerConfiguration.class,
        GsConventionConfiguration.class,
        GsRoutesConfiguration.class,
        NftCrawlerConfiguration.class,
        NftConventionConfiguration.class,
        NftMarketplaceConfiguration.class,
        NftOrmConfiguration.class,
        NftSearchConfiguration.class
})
public class NftConfiguration {

    @Value("${auth.locks: 200}")
    private int lockSize;

    @Bean
    public SimpleLockManger simpleLockManger(){
        return new SimpleLockManger(lockSize);
    }
}
