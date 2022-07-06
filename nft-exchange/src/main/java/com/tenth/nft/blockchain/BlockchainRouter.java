package com.tenth.nft.blockchain;

import com.tenth.nft.protobuf.NftSearch;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * @author shijie
 */
public class BlockchainRouter {

    private BlockchainGateway mock;

    private BlockchainGateway[] gateways;

    public BlockchainRouter(BlockchainGateway[] gateways) {
        this.gateways = gateways;
    }

    public BlockchainRouter(BlockchainGateway mock) {
        this.mock = mock;
    }

    public BlockchainGateway get(String blockchain) {

        if(null != mock){
            return mock;
        }

        Optional<BlockchainGateway> found = Arrays.stream(gateways)
                .filter(gateway -> gateway.getBlockchain().equals(blockchain))
                .findFirst();
        if(found.isPresent()){
            return found.get();
        }
        throw new UnsupportedOperationException("不支持的blockchain: " + blockchain);
    }
}
