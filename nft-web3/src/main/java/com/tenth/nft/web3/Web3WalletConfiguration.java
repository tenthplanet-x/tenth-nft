package com.tenth.nft.web3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @author shijie
 */
@Configuration
@ComponentScan("com.tenth.nft.web3")
public class Web3WalletConfiguration {

    @Value("${web3.network}")
    private String network;

    @Bean
    public Web3j create(){
        Web3j web3j = Web3j.build(new HttpService(network));
        return web3j;
    }

}
