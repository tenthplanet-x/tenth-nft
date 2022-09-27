package com.tenth.nft.marketplace.web3.dao.expression;

import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftAssetsUpdate;
import com.tpulse.gs.convention.dao.annotation.SimpleWriteParam;

/**
 * @author shijie
 */
public class Web3NftAssetsUpdate extends AbsNftAssetsUpdate {

    @SimpleWriteParam
    private TokenMintStatus mintStatus;

    public TokenMintStatus getMintStatus() {
        return mintStatus;
    }

    public static Builder newWeb3Builder(){
        return new Builder();
    }

    public static class Builder extends AbsNftAssetsUpdate.Builder{

        Web3NftAssetsUpdate update;

        public Builder mintStatus(TokenMintStatus mintStatus) {
            update.mintStatus = mintStatus;
            return this;
        }

        @Override
        protected AbsNftAssetsUpdate newUpdate() {
            update = new Web3NftAssetsUpdate();
            return update;
        }
    }
}
