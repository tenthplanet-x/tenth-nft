package com.tenth.nft.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 * @createdAt 2022/6/16 16:36
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
@Import(NftAppConfiguration.class)
public class TestNftApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestNftApplication.class, args);
    }
}
