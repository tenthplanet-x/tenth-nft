package com.tenth.nft.web3.dao;

import com.tenth.nft.web3.entity.Web3Wallet;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/18 15:53
 */
@Component
public class Web3WalletDao extends SimpleDao<Web3Wallet> {

    public Web3WalletDao() {
        super(Web3Wallet.class);
    }
}