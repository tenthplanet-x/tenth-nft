package com.tenth.nft.web3.dto;

/**
 * @author shijie
 */
public class EthWeb3WalletBalance extends Web3WalletBalance{

    public EthWeb3WalletBalance(String balance) {
        setCurrency("ETH");
        setBalance(balance);
    }

}
