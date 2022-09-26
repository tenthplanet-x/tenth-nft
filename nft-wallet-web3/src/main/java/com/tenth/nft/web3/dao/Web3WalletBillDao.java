package com.tenth.nft.web3.dao;

import com.tenth.nft.web3.entity.Web3WalletBill;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/08/19 12:00
 */
@Component
public class Web3WalletBillDao extends SimpleDao<Web3WalletBill> {

    public Web3WalletBillDao() {
        super(Web3WalletBill.class);
    }
}