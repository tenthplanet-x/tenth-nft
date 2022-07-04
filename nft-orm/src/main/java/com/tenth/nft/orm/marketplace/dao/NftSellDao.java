package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftSell;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/06/21 15:27
 */
@Component
public class NftSellDao extends SimpleDao<NftSell> {

    public NftSellDao() {
        super(NftSell.class);
    }
}