package com.tenth.nft.exchange.buildin;

import com.tenth.nft.convention.routes.exchange.ListingExpireCheckRouteRequest;
import com.tenth.nft.convention.routes.exchange.OfferExpireCheckRouteRequest;
import com.tenth.nft.orm.marketplace.dao.NftListingNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.NftOfferNoCacheDao;
import com.tenth.nft.orm.marketplace.dao.expression.NftListingQuery;
import com.tenth.nft.orm.marketplace.dao.expression.NftOfferQuery;
import com.tenth.nft.protobuf.NftExchange;
import com.tpulse.gs.router.client.RouteClient;
import com.tpulse.gs.scheduer.GsJob;
import com.tpulse.gs.scheduer.GsScheduler;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SmartApplicationListener;

/**
 * @author shijie
 */
@Configuration
public class NftExchangeScheduleConfiguration implements SmartApplicationListener {


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ApplicationStartedEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        GsScheduler gsScheduler = ((ApplicationStartedEvent)event).getApplicationContext().getBean(GsScheduler.class);
        NftListingNoCacheDao nftListingNoCacheDao = ((ApplicationStartedEvent)event).getApplicationContext().getBean(NftListingNoCacheDao.class);
        NftOfferNoCacheDao nftOfferNoCacheDao = ((ApplicationStartedEvent)event).getApplicationContext().getBean(NftOfferNoCacheDao.class);
        RouteClient routeClient = ((ApplicationStartedEvent)event).getApplicationContext().getBean(RouteClient.class);
        gsScheduler.schedule(
                "0 * * * * ? *",
                new GsJob("expire-check") {
                    @Override
                    protected void run() throws Exception {

                        long currentTime = System.currentTimeMillis();
                        nftListingNoCacheDao.find(NftListingQuery.newBuilder().expireAtLt(currentTime).build())
                                .stream()
                                .forEach(listing -> {
                                    routeClient.send(
                                            NftExchange.LISTING_EXPIRE_CHECK_IC.newBuilder()
                                                    .setAssetsId(listing.getAssetsId())
                                                    .setListingId(listing.getId())
                                                    .build(),
                                            ListingExpireCheckRouteRequest.class

                                    );
                                });
                        ;

                        nftOfferNoCacheDao.find(NftOfferQuery.newBuilder().expireAtLt(currentTime).build())
                                .stream()
                                .forEach(offer -> {
                                    routeClient.send(
                                            NftExchange.OFFER_EXPIRE_CHECK_IC.newBuilder()
                                                    .setAssetsId(offer.getAssetsId())
                                                    .setOfferId(offer.getId())
                                                    .build(),
                                            OfferExpireCheckRouteRequest.class

                                    );
                                });
                        ;

                    }
                }
        );


    }
}
