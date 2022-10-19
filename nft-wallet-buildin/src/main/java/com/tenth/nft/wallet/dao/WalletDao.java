package com.tenth.nft.wallet.dao;

import com.tenth.nft.wallet.entity.Wallet;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/08 19:17
 */
@Component
public class WalletDao extends SimpleDao<Wallet> {

    public WalletDao() {
        super(Wallet.class);
    }
}