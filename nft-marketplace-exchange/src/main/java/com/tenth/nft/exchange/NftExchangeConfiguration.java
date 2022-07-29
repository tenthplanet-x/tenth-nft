package com.tenth.nft.exchange;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.exchange")
@Import(NftExchangeScheduleConfiguration.class)
public class NftExchangeConfiguration {

}
