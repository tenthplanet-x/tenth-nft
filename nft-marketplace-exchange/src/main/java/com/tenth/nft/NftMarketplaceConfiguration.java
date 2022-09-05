package com.tenth.nft;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author shijie
 */
@Configuration
@ComponentScan({"com.tenth.nft.exchange", "com.tenth.nft.assets"})
public class NftMarketplaceConfiguration {

}
