package com.tenth.nft.marketplace.web3.dao.expression;

import com.tenth.nft.marketplace.common.dao.expression.AbsNftBuyOrderUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author shijie
 */
public class Web3NftBuyOrderUpdate extends AbsNftBuyOrderUpdate {

    @SimpleWriteParam
    private String txn;

    public String getTxn() {
        return txn;
    }

    public static Builder newWeb3Builder(){
        return new Builder();
    }

    public static class Builder extends AbsNftBuyOrderUpdate.Builder{

        private Web3NftBuyOrderUpdate update = new Web3NftBuyOrderUpdate();

        public Builder txn(String txn) {
            update.txn = txn;
            return this;
        }

        @Override
        protected AbsNftBuyOrderUpdate newUpdate() {
            return update;
        }
    }
}
