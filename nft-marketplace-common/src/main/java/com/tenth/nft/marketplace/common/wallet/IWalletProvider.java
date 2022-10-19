package com.tenth.nft.marketplace.common.wallet;

import com.tenth.nft.convention.wallet.WalletOrderBizContent;

/**
 * @author shijie
 */
public interface IWalletProvider {

    /**
     * token for pay
     * @param walletOrder
     * @return
     */
    String createToken(WalletOrderBizContent walletOrder);

    /**
     * pay channel
     * @return
     */
    String getChannel();

    /**
     *
     * @return
     */
    String getBlockchain();


}
