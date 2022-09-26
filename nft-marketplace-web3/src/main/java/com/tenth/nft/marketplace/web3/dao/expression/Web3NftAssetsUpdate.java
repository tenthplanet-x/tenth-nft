package com.tenth.nft.marketplace.web3.dao.expression;

import com.tenth.nft.convention.web3.utils.TokenMintStatus;
import com.tenth.nft.marketplace.common.dao.expression.AbsNftAssetsUpdate;

/**
 * @author shijie
 */
public class Web3NftAssetsUpdate extends AbsNftAssetsUpdate {

    private TokenMintStatus mintStatus;

    public TokenMintStatus getMintStatus() {
        return mintStatus;
    }

    public static Builder newWeb3Builder(){
        return new Builder();
    }

    public static class Builder extends AbsNftAssetsUpdate.Builder{

        Web3NftAssetsUpdate update = new Web3NftAssetsUpdate();

        public Builder mintStatus(TokenMintStatus mintStatus) {
            update.mintStatus = mintStatus;
            return this;
        }

        @Override
        protected AbsNftAssetsUpdate newUpdate() {
            return update;
        }
    }
}
