package com.tenth.nft.marketplace.rec;

import com.tpulse.gs.router.client.RouteClient;
import com.tpulse.gs.scheduer.GsJob;
import com.tpulse.gs.scheduer.GsScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SmartApplicationListener;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.marketplace.rec")
public class NftMarketplaceRecConfiguration implements SmartApplicationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(NftMarketplaceRecConfiguration.class);

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ApplicationStartedEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        GsScheduler scheduler = ((ApplicationStartedEvent)event).getApplicationContext().getBean(GsScheduler.class);
        scheduler.schedule(
                "0 * * * * ?",
                new GsJob("nft-marketplace-rec") {
                    @Override
                    protected void run() throws Exception {

                        try{
                            RouteClient routeClient = ((ApplicationStartedEvent)event).getApplicationContext().getBean(RouteClient.class);
//                            routeClient.send(
//                                    NftMarketplaceStats.VOLUME_STATS_IC.newBuilder()
//                                            .setTime(System.currentTimeMillis())
//                                            .build(),
//                                    VolumeStatsRouteRequest.class
//                            );
                        }catch (Exception e){
                            LOGGER.error("", e);
                        }
                    }
                }
        );

    }


}
