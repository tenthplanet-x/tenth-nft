package com.tenth.nft.search;

import com.tenth.nft.search.lucenedao.NftAssetsLuceneDao;
import com.tenth.nft.search.lucenedao.NftCollectionLuceneDao;
import com.tpulse.gs.lucenedb.GsLucenedbConfiguration;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.SmartApplicationListener;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.search")
@Import(GsLucenedbConfiguration.class)
public class NftSearchConfiguration implements SmartApplicationListener {


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ApplicationStartedEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        NftAssetsLuceneDao nftAssetsLuceneDao = ((ApplicationStartedEvent)event).getApplicationContext().getBean(NftAssetsLuceneDao.class);
        nftAssetsLuceneDao.init();

        NftCollectionLuceneDao nftCollectionLuceneDao = ((ApplicationStartedEvent)event).getApplicationContext().getBean(NftCollectionLuceneDao.class);
        nftCollectionLuceneDao.init();

    }
}
