package com.tenth.nft.marketplace.web3.entity;

import com.tenth.nft.marketplace.common.entity.AbsNftOrder;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author shijie
 */
@Document("tpulse.web3_nft_accept_order")
public class Web3NftAcceptOrder extends AbsNftOrder {

    private String txn;

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }
}
