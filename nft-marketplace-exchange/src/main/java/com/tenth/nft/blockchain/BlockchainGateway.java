package com.tenth.nft.blockchain;

import java.util.concurrent.Future;

/**
 * @author shijie
 */
public interface BlockchainGateway {

    public Future<BlockchainContract> getGlobalNftContract();

    public Future<BlockchainContract> getContract(String contractAddress);

    Future<String> mint(BlockchainContract contract);

    String getBlockchain();
}
