package com.tenth.nft.boot;

import com.tenth.nft.convention.NftConventionConfiguration;
import com.tpulse.gs.GsRoutesConfiguration;
import com.tpulse.gs.convention.GsConventionConfiguration;
import com.tpulse.gs.convention.dao.mysql.SimpleMysqlConfiguration;
import com.tpulse.gs.router.client.local.LocalRouteClientConfiguration;
import com.tpulse.gs.scheduer.GsSchedulerConfiguration;
import com.wallan.router.HttpRouterEndpointConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 * @createdAt 2022/6/15 09:25
 */
@Configuration
@Import({
        SimpleMysqlConfiguration.class,
        GsSchedulerConfiguration.class,
        HttpRouterEndpointConfiguration.class,
        LocalRouteClientConfiguration.class,
        GsConventionConfiguration.class,
        GsRoutesConfiguration.class,
        NftConventionConfiguration.class
})
public class NftAppConfiguration {

}
