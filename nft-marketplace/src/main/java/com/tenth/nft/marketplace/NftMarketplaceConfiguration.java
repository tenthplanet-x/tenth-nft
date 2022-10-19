package com.tenth.nft.marketplace;

import com.tpulse.gs.oss.qiniu.QiniuGsOssConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.marketplace")
@Import(QiniuGsOssConfiguration.class)
public class NftMarketplaceConfiguration {
}
