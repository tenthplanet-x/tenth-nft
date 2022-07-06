package com.tenth.nft.blockchain;

import com.tenth.nft.blockchain.mock.MockBlockchainGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shijie
 */
@Configuration
public class NftBlockchainConfiguration {

    @Bean
    public MockBlockchainGateway mockBlockchainGateway(){
        return new MockBlockchainGateway();
    }

    @Bean
    public BlockchainRouter blockchainRouter(BlockchainGateway[] gateways, MockBlockchainGateway mock){
        if(null != mock){
            return new BlockchainRouter(mock);
        }else{
            return new BlockchainRouter(gateways);
        }

    }

}
