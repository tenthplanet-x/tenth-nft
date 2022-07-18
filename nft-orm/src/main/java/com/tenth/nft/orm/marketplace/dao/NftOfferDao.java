package com.tenth.nft.orm.marketplace.dao;

import com.tenth.nft.orm.marketplace.entity.NftOffer;
import com.tpulse.gs.convention.dao.SimpleDao;
import org.springframework.stereotype.Component;

/**
 * @author gs-orm-generator
 * @createdAt 2022/07/18 08:24
 */
@Component
public class NftOfferDao extends SimpleDao<NftOffer> {

    public NftOfferDao() {
        super(NftOffer.class);
    }
}