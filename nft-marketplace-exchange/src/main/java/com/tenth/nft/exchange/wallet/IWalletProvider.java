package com.tenth.nft.wallet.wallet;

import java.util.List;

/**
 * @author shijie
 */
public interface IWalletProvider {

    WalletBalance getBalance(Long uid, String currency);

    List<WalletBalance> getBalances(Long uid, List<String> currency);

}
