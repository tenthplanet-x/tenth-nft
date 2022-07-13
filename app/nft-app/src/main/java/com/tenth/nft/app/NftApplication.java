package com.tenth.nft.app;

import com.tpulse.gs.convention.dao.mysql.SimpleMysqlConfiguration;
import com.tpulse.gs.router.client.remote.RemoteRouteClientConfiguration;
import com.tpulse.gs.router.server.RemoteRouteServerConfiguration;
import com.tpulse.gs.router.tribe.client.RouteTribeClientConfiguration;
import com.wallan.router.HttpRouterEndpointConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
@Import({
        NftConfiguration.class,
        SimpleMysqlConfiguration.class,
        RouteTribeClientConfiguration.class,
        RemoteRouteServerConfiguration.class,
        RemoteRouteClientConfiguration.class,
        HttpRouterEndpointConfiguration.class,
})
public class NftApplication {

    public static void main(String[] args) {
        SpringApplication.run(NftApplication.class, args);
    }
}
