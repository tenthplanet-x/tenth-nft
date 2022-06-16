package com.tenth.nft.crawler;

import com.tenth.nft.convention.utils.Crons;
import com.tenth.nft.crawler.sdk.alchemy.AlchemyProperties;
import com.tenth.nft.crawler.service.NftBotProcessService;
import com.tpulse.gs.oss.qiniu.QiniuGsOssConfiguration;
import com.tpulse.gs.scheduer.GsJob;
import com.tpulse.gs.scheduer.GsScheduler;
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

@Configuration
@ComponentScan("com.tenth.nft.crawler")
@Import(QiniuGsOssConfiguration.class)
public class NftCrawlerConfiguration implements SmartApplicationListener {

    private Logger LOGGER = LoggerFactory.getLogger(NftCrawlerConfiguration.class);

    @Bean
    @ConfigurationProperties("nft.crawler")
    public NftCrawlerProperties nftCrawlerProperties(){
        return new NftCrawlerProperties();
    }

    @Bean
    @ConfigurationProperties("nft.alchemy")
    public AlchemyProperties alchemyProperties(){
        return new AlchemyProperties();
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ApplicationStartedEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        GsScheduler gsScheduler = ((ApplicationStartedEvent)event).getApplicationContext().getBean(GsScheduler.class);
        NftBotProcessService nftBotProcessService = ((ApplicationStartedEvent)event).getApplicationContext().getBean(NftBotProcessService.class);
        gsScheduler.schedule(
                Crons.minute(),
                new GsJob("nft-bot") {
                    @Override
                    protected void run() throws Exception {
                        try{
                            nftBotProcessService.execute();
                        }catch (Exception e){
                            LOGGER.error("", e);
                        }
                    }
                }
        );
    }
}
