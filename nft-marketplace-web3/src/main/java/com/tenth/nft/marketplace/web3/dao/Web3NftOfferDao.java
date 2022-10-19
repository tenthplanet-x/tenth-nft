package com.tenth.nft.marketplace.web3.dao;

import com.tenth.nft.marketplace.common.dao.AbsNftOfferDao;
import com.tenth.nft.marketplace.web3.entity.Web3NftOffer;
import org.springframework.stereotype.Component;

/**
 * @author shijie
 */
@Component
public class Web3NftOfferDao extends AbsNftOfferDao<Web3NftOffer> {

    public Web3NftOfferDao() {
        super(Web3NftOffer.class);
    }
}
