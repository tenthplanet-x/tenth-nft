package com.tenth.nft.marketplace.stats;

import com.tenth.nft.convention.routes.marketplace.rec.RecDoStatsRouteRequest;
import com.tenth.nft.protobuf.NftMarketplaceRec;
import com.tenth.nft.protobuf.NftMarketplaceStats;
import com.tpulse.gs.router.client.RouteClient;
import com.tpulse.gs.scheduer.GsJob;
import com.tpulse.gs.scheduer.GsScheduler;
import com.tpulse.gs.scheduer.GsSchedulerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.SmartApplicationListener;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.marketplace.stats")
@Import(GsSchedulerConfiguration.class)
public class NftMarketplaceStatsConfiguration implements SmartApplicationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(NftMarketplaceStatsConfiguration.class);

    @Bean
    @ConfigurationProperties(prefix = "nft.stats")
    public NftStatsProperties nftStatsProperties(){
        return new NftStatsProperties();
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ApplicationStartedEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        GsScheduler scheduler = ((ApplicationStartedEvent)event).getApplicationContext().getBean(GsScheduler.class);
        scheduler.schedule(
                "0 * * * * ?",
                new GsJob("nft-marketplace-stats") {
                    @Override
                    protected void run() throws Exception {

                        try{
                            RouteClient routeClient = ((ApplicationStartedEvent)event).getApplicationContext().getBean(RouteClient.class);
                            routeClient.send(
                                    NftMarketplaceRec.REC_DO_STATS_IC.newBuilder()
                                            .build(),
                                    RecDoStatsRouteRequest.class
                            );
                        }catch (Exception e){
                            LOGGER.error("", e);
                        }
                    }
                }
        );

    }
}
