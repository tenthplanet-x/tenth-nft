package com.tenth.nft.search;

import com.tenth.nft.orm.ExternalNftOrmConfiguration;
import com.tenth.nft.search.lucene.LucenedbProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.search")
@Import(ExternalNftOrmConfiguration.class)
public class NftSearchConfiguration {

    @Bean
    @ConfigurationProperties("lucenedb")
    public LucenedbProperties lucenedbProperties(){
        return new LucenedbProperties();
    }
}
