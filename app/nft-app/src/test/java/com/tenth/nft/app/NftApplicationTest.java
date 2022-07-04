package com.tenth.nft.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author shijie
 */
@SpringBootApplication
@Import({
        NftApplication.class
})
public class NftApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(NftApplicationTest.class, args);
    }
}
