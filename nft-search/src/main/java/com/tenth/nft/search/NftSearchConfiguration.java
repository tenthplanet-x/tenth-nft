package com.tenth.nft.search;

import com.tpulse.gs.lucenedb.GsLucenedbConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.search")
@Import(GsLucenedbConfiguration.class)
public class NftSearchConfiguration {

}
