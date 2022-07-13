package com.tenth.nft.blockchain.mock;

import com.google.common.util.concurrent.Futures;
import com.tenth.nft.blockchain.BlockchainContract;
import com.tenth.nft.blockchain.BlockchainGateway;
import com.tenth.nft.convention.blockchain.NftTokens;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author shijie
 */
public class MockBlockchainGateway implements BlockchainGateway {

    private String tokenStarndard = "ERC-1155";
    private String contractAddress = "0x05a4fcd4e19f0852fcde5484d13ca9f7999f59f6";

    @Override
    public Future<BlockchainContract> getGlobalNftContract() {

        BlockchainContract contract = new BlockchainContract();
        contract.setTokenStandard(tokenStarndard);
        contract.setAddress(contractAddress);
        return Futures.immediateFuture(contract);
    }

    @Override
    public Future<BlockchainContract> getContract(String contractAddress) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Future<String> mint(BlockchainContract contract) {
        return Futures.immediateFuture(NftTokens.randomHexToken());
    }

    @Override
    public String getBlockchain() {
        return "mock";
    }
}
