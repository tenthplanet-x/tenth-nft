package com.tenth.nft.wallet.wallet;

import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class WalletProviderFactory {

    public IWalletProvider get(String walletBlockchain) {
        throw new UnsupportedOperationException();
    }
}
